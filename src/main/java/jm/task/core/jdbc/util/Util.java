package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String URL = "jdbc:mysql://localhost:3306/mydbtest?useSSL=no&serverTimezone=UTC";
    private static String USERNAME = "root";
    private static String PASSWORD = "root";

    public static Connection conn = null;


    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void close() throws Exception {
        conn.close();
    }
}
