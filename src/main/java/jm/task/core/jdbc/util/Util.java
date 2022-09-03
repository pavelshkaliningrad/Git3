package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Util {
    private  static String url = "jdbc:mysql://localhost/database1";
    private  static String userName = "root";
    private static String password = "1234";

    public static Statement getStatement () throws SQLException {return  Util.getConnection().createStatement();}
    public  static  Connection getConnection () throws SQLException {
        return DriverManager.getConnection(Util.url,userName, password);

    }
    public static Session getCurrentSession() {
        // Hibernate 5.4 SessionFactory example without XML
        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
        settings.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
        settings.put("hibernate.connection.url",
                url);
        settings.put("hibernate.connection.username", userName);
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");
        settings.put("hibernate.format_sql", "true");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(User.class);
        Metadata metadata = metadataSources.buildMetadata();

        // here we build the SessionFactory (Hibernate 5.4)
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.getCurrentSession();
        return session;
    }

}
