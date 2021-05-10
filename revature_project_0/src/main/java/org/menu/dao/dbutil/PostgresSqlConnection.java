package org.menu.dao.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresSqlConnection {
    private PostgresSqlConnection(){}

    private static Connection connection;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url="jdbc:postgresql://testdb.ccqsevbt8frp.us-east-2.rds.amazonaws.com:5432/postgres";
        String username="postgres";
        String password="ageomon16";
        return connection= DriverManager.getConnection(url,username,password);
    }
}
