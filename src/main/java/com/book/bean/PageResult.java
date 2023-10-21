package com.book.bean;

import lombok.Data;

import java.util.List;
@Data
public class PageResult<T>{
    private int total;
    private List<T> data;
}
