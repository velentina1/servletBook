package com.book.dao;

import com.book.bean.User;

import java.sql.SQLException;

public interface UserDao {
    public User selectUserByUsername(String username);

    boolean insertUser(User user);
}
