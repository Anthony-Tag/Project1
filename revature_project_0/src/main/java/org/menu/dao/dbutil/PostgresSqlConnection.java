package org.menu.dao.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresSqlConnection {
    private PostgresSqlConnection(){}

    private static Connection connection;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("");
        String url="";
        String username="";
        String password="";
        return connection= DriverManager.getConnection(url,username,password);
    }
}
