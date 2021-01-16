package com.igeek.login.service;

import com.igeek.login.dao.UserDao;
import com.igeek.login.entity.User;
import com.igeek.login.util.JDBCUtils;

import java.sql.SQLException;

public class UserService {

    private UserDao dao = new UserDao();

    public User login(String username, String password){
        User user =null;
        try {
            user = dao.selectOne(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConn();
        }

        return user;
    }

}
