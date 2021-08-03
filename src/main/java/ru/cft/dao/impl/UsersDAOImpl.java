package ru.cft.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.cft.configuration.HibernateConfig;
import ru.cft.dao.UsersDAO;
import ru.cft.model.Car;
import ru.cft.model.User;

import java.util.Optional;

public class UsersDAOImpl implements UsersDAO {

    private HibernateConfig config;

    @Override
    public Optional<User> getUserByLogin(String login) {

        Optional<User> user;
        Session session = null;
        try {
            session = config.getSessionFactory().openSession();
            user = session
                    .createQuery("SELECT a FROM User a WHERE a.login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResultOptional();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public Optional<User> getAllUsers() {

        Optional<User> users;
        Session session = null;
        try {
            session = config.getSessionFactory().openSession();
            users = session
                    .createQuery("SELECT a FROM User a", User.class)
                    .uniqueResultOptional();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }
}
