package com.book.service.impl;

import com.book.bean.User;
import com.book.dao.UserDao;
import com.book.dao.impl.UserDaoImpl;
import com.book.service.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    @Override
    public User selectUserByName(String username) {
        return userDao.selectUserByUsername(username);
    }

    @Override
    public boolean addUser(User user) {
        return userDao.insertUser(user);
    }
}
