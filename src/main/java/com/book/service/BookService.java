package com.book.service;

import com.book.bean.Book;
import com.book.bean.BookType;

import java.util.List;

public interface BookService {
    List<BookType> queryBookTypes();

    List<Book> bookList(int pageSize, int pageIndex, String bookname, String booktype);

    int count();
}
