package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest?useSSL=no&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) throws SQLException, Exception {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        Object[][] usersData = new Object[][]{
                {"Din", "Smit", (byte) 30},
                {"Sem", "Smit", (byte) 35},
                {"Ivan", "Ivanov", (byte) 25},
                {"Tom", "White", (byte) 15},
        };

        for (Object[] userData : usersData) {
            userService.saveUser((String) userData[0], (String) userData[1], (Byte) userData[2]);
            System.out.println("User с именем – " + userData[0] + " добавлен в базу данных");

        }

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
