package com.book.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.book.bean.Book;
import com.book.bean.BookType;
import com.book.dao.BookDao;
import com.book.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private QueryRunner queryRunner = new QueryRunner(DruidUtil.getDataSource());
    @Override
    public List<Book> selectAllBooks(int pageSize, int pageIndex, String bookname, String booktype) {
        try {
            String sql = "select b1.bookid,b1.bookname,b1.publisher,b1.author,b1.remain,b2.tname as booktype from tb_books b1 left join tb_booktype b2 on b1.booktype = b2.tid where 1=1";
            HashMap<String, String> mapParams = new HashMap<>();
            List<Book> bookList;
            if (StrUtil.isNotEmpty(bookname)) {
                mapParams.put("bookname", "%" + bookname + "%");
                sql = sql + " and bookname like ?";
            }
            if (StrUtil.isNotEmpty(booktype)) {
                mapParams.put("booktype", "%" + booktype + "%");
                sql = sql + " and booktype = ?";
            }
            sql = sql + " limit " + pageIndex * pageSize + "," + pageSize;

            if (mapParams.size() > 0) {
                Object[] objects = mapParams.values().toArray();
                bookList = queryRunner.query(sql, new BeanListHandler<>(Book.class), objects);
            } else {
                bookList = queryRunner.query(sql,new BeanListHandler<>(Book.class));
            }
            return bookList;
        }catch (SQLException e) {
                throw new RuntimeException(e);
        }

    }

    @Override
    public int count() {
    try {
        String sql = "select count(*) from tb_books";
        Number number = queryRunner.query(sql,new ScalarHandler<>());
        return number.intValue();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BookType> selectAllBookTypes() {
        try {
            String sql = "select * from tb_booktype";
            return queryRunner.query(sql,new BeanListHandler<>(BookType.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
