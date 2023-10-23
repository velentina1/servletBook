package com.book.dao;

import com.book.bean.BorrowInfo;
import com.book.bean.TreeMenu;

import java.util.List;

public interface BorrowDao {
    boolean selectBookisReturn(String ids);

    List<BorrowInfo> selectBorrowList();

    int borrowCount();

    boolean updateBorrowInfo(String borrowid);

    BorrowInfo selectBorrowInfoById(String borrowid);

    boolean deleteBorrowInfo(String borrowid);

    List<TreeMenu> getTreeMenus();

    boolean addBorrowInfo(BorrowInfo borrowInfo);
}
