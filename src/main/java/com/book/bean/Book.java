package com.book.bean;

import lombok.Data;

@Data
public class Book {
    private String bookid;
    private String bookname;
    private String publisher;
    private String author;
    private String booktype;
    private int remain;
}
