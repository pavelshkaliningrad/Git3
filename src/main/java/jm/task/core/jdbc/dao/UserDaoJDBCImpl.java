package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Util.getStatement().execute("CREATE TABLE Users (id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT , name  CHAR(50), lastName CHAR(50), age TINYINT)");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы");
        }
    }

    public void dropUsersTable() {
        try {
            Util.getStatement().execute("DROP TABLE Users");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Util.getStatement().execute(String.format("INSERT INTO Users (name,lastName,age) VALUES (\"%s\",\"%s\",%d);",name,lastName,age));
        }
        catch (SQLException e) {
            System.out.println("Ошибка при создании нового пользователя");
        }
    }

    public void removeUserById(long id) {
        try {
            Util.getStatement().execute(String.format("DELETE FROM Users WHERE id=%d",id));
        }
        catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя");
        }
    }

    public List<User> getAllUsers() {
        ResultSet resultSet ;
        List<User> userList = new ArrayList<>();
        Long id;
        String name;
        String lastname;
        Byte age;
        try {
            resultSet = Util.getStatement().executeQuery("SELECT * FROM database1.users");
            while (!resultSet.isLast()) {
                resultSet.next();
                id = resultSet.getLong("id");
                name = resultSet.getString("name");
                lastname = resultSet.getString("lastName");
                age = resultSet.getByte("age");
                userList.add(new User(name,lastname,age));
                userList.get(userList.size()-1).setId(id);
            }
        }
        catch (SQLException e) {
            System.out.println("Ошибка при возвражении всех пользователей");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            Util.getStatement().execute(String.format("TRUNCATE TABLE database1.users"));
        }
        catch (SQLException e) {
            System.out.println("Ошибка при очистки таблицы пользователей");
        }
    }
}
