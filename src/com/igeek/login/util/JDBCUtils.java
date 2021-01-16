package com.igeek.login.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtils {
    private static ComboPooledDataSource pool = new ComboPooledDataSource("mysql");

    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    //获得连接对象
    public static Connection getConn(){
        Connection conn = tl.get();
        try {
            if (conn == null || conn.isClosed()){
                conn = pool.getConnection();
                tl.set(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //释放连接对象
    public static void closeConn(){
        Connection conn = tl.get();
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
