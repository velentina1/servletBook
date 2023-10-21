package com.book.bean;

import lombok.Data;

@Data
public class BorrowInfo {
    private String borrowid;
    private String bookid;
    private String borrower;
    private String phone;
    private String borrowtime;
    private String returntime;
    private int isreturn;

}
