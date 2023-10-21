package com.book.dao.impl;

import com.book.bean.User;
import com.book.dao.UserDao;
import com.book.util.DruidUtil;
import com.book.util.MD5Util;
import com.book.util.UUIDUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private QueryRunner queryRunner = new QueryRunner(DruidUtil.getDataSource());

    @Override
    public User selectUserByUsername(String username){
        String sql = "select * from tb_user where username = ?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(User.class),username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insertUser(User user) {
        try {
            String sql = "insert into tb_user(uid,username,password) values (?,?,?)";
            user.setUid(UUIDUtil.uuid());
            user.setPassword(MD5Util.generateSaltPassword(user.getPassword()));
            int i = queryRunner.update(sql, user.getUid(), user.getUsername(), user.getPassword());
            return i > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
