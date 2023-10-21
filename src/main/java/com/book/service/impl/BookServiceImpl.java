package com.book.service.impl;

import com.book.bean.Book;
import com.book.bean.BookType;
import com.book.dao.BookDao;
import com.book.dao.impl.BookDaoImpl;
import com.book.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    @Override
    public List<BookType> queryBookTypes() {
        return bookDao.selectAllBookTypes();
    }

    @Override
    public List<Book> bookList(int pageSize, int pageIndex, String bookname, String booktype) {
        return bookDao.selectAllBooks(pageSize,pageIndex,bookname,booktype);
    }

    @Override
    public int count() {
        return bookDao.count();
    }
}
