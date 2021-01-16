package com.igeek.login.dao;

import com.igeek.login.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class BaseDao<T> {

    private QueryRunner runner =new QueryRunner();

    //增删改

    //查询单个对象
    public T getBean(String sql,Class<T> clazz, Object...params) throws SQLException {
        T t = runner.query(JDBCUtils.getConn(), sql, new BeanHandler<>(clazz), params);
        return t;
    }

}
