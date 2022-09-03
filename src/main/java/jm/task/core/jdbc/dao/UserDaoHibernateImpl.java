package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE Users (id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT , name  CHAR(50), lastName CHAR(50), age TINYINT)").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE Users").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getCurrentSession();
        session.beginTransaction();
        session.save(new User(name,lastName,age));
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getCurrentSession();
        session.beginTransaction();
        session.remove(session.get(User.class, id));
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getCurrentSession();
        session.beginTransaction();
        Query query = session.createNativeQuery("select * from Users",User.class);
        List<User> listUsers = query.getResultList();
        session.getTransaction().commit();
        return listUsers;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE TABLE database1.users").executeUpdate();
        session.getTransaction().commit();
    }
}
