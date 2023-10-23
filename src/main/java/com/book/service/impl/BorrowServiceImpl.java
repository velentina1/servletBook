package com.book.service.impl;

import com.book.bean.BorrowInfo;
import com.book.bean.TreeMenu;
import com.book.dao.BorrowDao;
import com.book.dao.impl.BorrowDaoImpl;
import com.book.service.BorrowService;

import java.util.List;

public class BorrowServiceImpl implements BorrowService {
    private BorrowDao borrowDao = new BorrowDaoImpl();
    @Override
    public boolean selectBookIsReturn(String ids) {
        return borrowDao.selectBookisReturn(ids);
    }

    @Override
    public List<BorrowInfo> borrowList() {
        return borrowDao.selectBorrowList();
    }

    @Override
    public int count() {
        return borrowDao.borrowCount();
    }

    @Override
    public boolean returnBook(String borrowid) {
        return borrowDao.updateBorrowInfo(borrowid);
    }

    @Override
    public BorrowInfo selectBorrowById(String borrowid) {
        return borrowDao.selectBorrowInfoById(borrowid);
    }

    @Override
    public boolean deleteBorrow(String borrowid) {
        return borrowDao.deleteBorrowInfo(borrowid);
    }

    @Override
    public List<TreeMenu> queryTreeMenus() {
        return borrowDao.getTreeMenus();
    }

    @Override
    public boolean addBorrow(BorrowInfo borrowInfo) {
        return borrowDao.addBorrowInfo(borrowInfo);
    }


}
