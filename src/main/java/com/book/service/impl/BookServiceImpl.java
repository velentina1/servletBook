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

    @Override
    public boolean repeatBook(String author, String bookname) {
        return bookDao.repeatBook(author,bookname);
    }

    @Override
    public boolean addBook(Book book) {
        return bookDao.insertBook(book);
    }

    @Override
    public boolean updateBook(Book book) {
        return bookDao.editBook(book);
    }
    @Override
    public boolean deleteBook(String ids) {
        System.out.println(ids);
        boolean a = bookDao.deleteBookById(ids);
        return a;
    }

    @Override
    public Book selectBookById(String bookid) {
        return bookDao.selectBookById(bookid);
    }

    @Override
    public boolean updateBookNumber(String bookid) {
        return bookDao.updateBookNum(bookid);
    }

    @Override
    public boolean getBookRemain(String bookid) {
        return bookDao.getBookRemain(bookid);
    }

    @Override
    public void updateBookCount(String bookid) {
        bookDao.updateBookRemain(bookid);
    }
}
