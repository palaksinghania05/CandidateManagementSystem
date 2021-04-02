package com.wipro.candidate.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/candidate";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    public Connection createConnection(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("connection has been created");
        } catch (SQLException throwable) {
            System.err.println("Could not connect to Database");
            System.err.println(throwable.getMessage());
        }
        return connection;
    }

    public Connection getConnection(){
        return createConnection(URL, USERNAME, PASSWORD);
    }
    public static Connection getDBConn() {
        Connection con = null;
        //write code here
        DBUtil dbUtil = new DBUtil();
        return dbUtil.getConnection();
    }
}
