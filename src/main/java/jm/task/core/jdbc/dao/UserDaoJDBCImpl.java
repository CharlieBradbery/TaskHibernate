package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
        try {
            Util.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        try {
            Statement statement = Util.conn.createStatement();
            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(50), " +
                    " lastName VARCHAR (50), " +
                    " age TINYINT not NULL, " +
                    " PRIMARY KEY (id))";

            statement.executeUpdate(SQL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = Util.conn.createStatement();
            String SQL = "DROP TABLE IF EXISTS users ";
            statement.executeUpdate(SQL);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement statement = Util.conn.prepareStatement("insert into users(name, lastName, age) values (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = Util.conn.prepareStatement("delete from users where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            Statement statement = Util.conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }

            return users;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try{
            Statement statement = Util.conn.createStatement();
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
