package com.book.service;

import com.book.bean.BorrowInfo;
import com.book.bean.TreeMenu;

import java.util.List;

public interface BorrowService {
    boolean selectBookIsReturn(String ids);

    List<BorrowInfo> borrowList();

    int count();

    boolean returnBook(String borrowid);

    BorrowInfo selectBorrowById(String borrowid);

    boolean deleteBorrow(String borrowid);

    List<TreeMenu> queryTreeMenus();

    boolean addBorrow(BorrowInfo borrowInfo);
}
