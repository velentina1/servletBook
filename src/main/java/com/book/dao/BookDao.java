package com.book.dao;

import com.book.bean.Book;
import com.book.bean.BookType;

import java.util.List;

public interface BookDao {
    public List<Book> selectAllBooks(int pageSize, int pageIndex, String bookname, String booktype);

    int count();

    List<BookType> selectAllBookTypes();
}
