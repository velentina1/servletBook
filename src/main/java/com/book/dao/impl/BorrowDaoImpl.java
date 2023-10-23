package com.book.dao.impl;

import com.book.bean.Book;
import com.book.bean.BookType;
import com.book.bean.BorrowInfo;
import com.book.bean.TreeMenu;
import com.book.dao.BorrowDao;
import com.book.util.DruidUtil;
import com.book.util.UUIDUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowDaoImpl implements BorrowDao {
    private QueryRunner queryRunner = new QueryRunner(DruidUtil.getDataSource());

    @Override
    public boolean selectBookisReturn(String ids) {
        String[] idsArray = ids.split(",");
        String sql = "select * from tb_borrowinfo where bookid in(?)";
        try {
            List<BorrowInfo> borrowInfoList = queryRunner.query(sql, new BeanListHandler<>(BorrowInfo.class), idsArray);
            for (BorrowInfo borrowInfo : borrowInfoList) {
                if (borrowInfo.getIsreturn() == 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<BorrowInfo> selectBorrowList() {
        String sql = "select b1.borrowid,b2.bookname as bookid,b1.borrower,b1.phone,b1.borrowtime,b1.returntime,b1.isreturn from tb_borrowinfo b1 left join tb_books b2 on b1.bookid = b2.bookid order by borrowtime desc";
        List<BorrowInfo> list = null;
        try {
            list = queryRunner.query(sql, new BeanListHandler<>(BorrowInfo.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public int borrowCount() {
        String sql = "select count(*) from tb_borrowinfo";
        try {
            Number number = queryRunner.query(sql, new ScalarHandler<>());
            return number.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateBorrowInfo(String borrowid) {
        String sql = "update tb_borrowinfo set returntime = NOW(),isreturn = 1 where borrowid = ?";
        try {
            int count = queryRunner.update(sql, borrowid);
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BorrowInfo selectBorrowInfoById(String borrowid) {
        String sql = "select * from tb_borrowinfo where borrowid = ?";

        try {
            BorrowInfo borrowInfo = queryRunner.query(sql, new BeanHandler<>(BorrowInfo.class), borrowid);
            return borrowInfo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteBorrowInfo(String borrowid) {
        String sql = "delete from tb_borrowinfo where borrowid = ?";
        int count = 0;
        try {
            count = queryRunner.update(sql, borrowid);
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TreeMenu> getTreeMenus() {
        //创建一个list,返回结果
        ArrayList<TreeMenu> treeMenus = new ArrayList<>();
        try {
            //查询所有图书，然后做遍历，存放到对应的节点
            //先查种类
            String sql = "select * from tb_booktype";
            List<BookType> bookTypes = queryRunner.query(sql, new BeanListHandler<>(BookType.class));

            //先遍历bookTypes的list
            for (BookType bookType : bookTypes) {
                //每个booktype都当做一个节点
                //booktypeTreeMenu当前类型的节点
                TreeMenu booktypeTreeMenu = new TreeMenu();
                booktypeTreeMenu.setId(bookType.getTid());
                booktypeTreeMenu.setText(bookType.getTname());
                booktypeTreeMenu.setExpanded(false);
                //查询图书，将所有图书放到对应类型的节点下，也就是每个bookType节点的children属性
                //根据当前的类型查询,查询到当前类型下，有哪些书，将来存放的是书籍对应的节点对象
                List<TreeMenu> typechildrenBooks = new ArrayList<>();
                String tid = bookType.getTid();
                String  sql1 = "select * from tb_books where booktype = ?";
                //当前类型下的所有书籍
                List<Book> books = queryRunner.query(sql1, new BeanListHandler<>(Book.class), tid);
                //在把查到的book做遍历，把每一本书 ，绑定到treeMeanu节点上
                for (Book book : books) {
                    TreeMenu bookMenu = new TreeMenu();
                    bookMenu.setId(book.getBookid());
                    bookMenu.setText(book.getBookname());
                    bookMenu.setExpanded(false);
                    typechildrenBooks.add(bookMenu);
                }
                //将书籍类型节点的children属性，设置所有的书籍节点
                booktypeTreeMenu.setChildren(typechildrenBooks);
                treeMenus.add(booktypeTreeMenu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treeMenus;
    }

    @Override
    public boolean addBorrowInfo(BorrowInfo borrowInfo) {
        borrowInfo.setBorrowid(UUIDUtil.uuid());
        String sql = "insert into tb_borrowinfo values(?,?,?,?,?,?,?)";
        try {
            int count = queryRunner.update(sql, borrowInfo.getBorrowid(),borrowInfo.getBookid(), borrowInfo.getBorrower(), borrowInfo.getPhone(), borrowInfo.getBorrowtime(), borrowInfo.getReturntime(), borrowInfo.getIsreturn());
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
