package jm.task.core.jdbc.util;

import jm.task.core.jdbc.Main;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    public static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    private static String URL = "jdbc:mysql://localhost:3306/mydbtest?useSSL=no&serverTimezone=UTC";
    private static String USERNAME = "root";
    private static String PASSWORD = "root";

    public static Connection conn = null;


    public static SessionFactory createSessionFactory() {
        try {
            Configuration configuration = new Configuration()
                    .setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect")
                    .setProperty(Environment.HBM2DDL_AUTO, "none")
                    .setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver")
                    .setProperty(Environment.USER, USERNAME)
                    .setProperty(Environment.PASS, PASSWORD)
                    .setProperty(Environment.URL, URL);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) { // Singleton
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }

    public static void closeHibernate() throws Exception {
        createSessionFactory().close();
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void close() throws Exception {
        conn.close();
    }
}
