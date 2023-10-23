package com.book.bean;

import lombok.Data;

import java.util.List;

@Data
public class TreeMenu {
    private String id;
    private String text;
    private boolean expanded;
    private List<TreeMenu> children;
}
