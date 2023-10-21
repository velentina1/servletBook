package com.book.bean;

import lombok.Data;

@Data
public class ResultVo<T> {
    private boolean isOK;
    private String message;
    private T t;
}
