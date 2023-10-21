package com.book.service.impl;

import com.book.dao.BorrowDao;
import com.book.dao.impl.BorrowDaoImpl;
import com.book.service.BorrowService;

public class BorrowServiceImpl implements BorrowService {
    private BorrowDao borrowDao = new BorrowDaoImpl();
    @Override
    public boolean selectBookIsReturn(String ids) {
        return borrowDao.selectBookisReturn(ids);
    }


}
