package com.igeek.login.dao;

import com.igeek.login.entity.User;

import java.sql.SQLException;

public class UserDao extends BaseDao<User> {
    //通过姓名和密码查询用户信息
    public User selectOne(String username,String password) throws SQLException {
        User user = this.getBean("select * from user where name = ? and pwd = ?", User.class, username, password);
        return user;
    }
}
