package com.book.service;

import com.book.bean.User;

public interface UserService {
    public User selectUserByName(String username);

    boolean addUser(User user);
}
