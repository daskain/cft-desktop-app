package ru.cft.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.cft.configuration.HibernateConfig;
import ru.cft.dao.ClientsDAO;
import ru.cft.model.Car;
import ru.cft.model.Client;

import java.util.List;
import java.util.Optional;

public class ClientsDAOImpl implements ClientsDAO {

    private HibernateConfig config;

    @Override
    public List<Client> getAllClients() {

        List<Client> clientList;
        Session session = null;
        try {
            session = config.getSessionFactory().openSession();
            clientList = session
                    .createQuery("SELECT a FROM Client a")
                    .getResultList();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return clientList;
    }

    @Override
    public Optional<Client> getClientByPassport(String passport) {

        Optional<Client> client;
        Session session = null;
        try {
            session = config.getSessionFactory().openSession();
            client = session
                    .createQuery("SELECT a FROM Client a WHERE a.passport = :passport")
                    .setParameter("passport", passport)
                    .uniqueResultOptional();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return client;
    }

    @Override
    public void deleteByPassport(String passport) {
        Session session = null;
        Transaction transaction;
        try {
            session = config.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session
                    .createQuery("DELETE FROM Client a WHERE a.passport = :passport")
                    .setParameter("passport", passport)
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void editClient(Client client) {
        Session session = null;
        Transaction transaction;
        try {
            session = config.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(client);
            transaction.commit();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void addClient(Client client) {
        Session session = null;
        Transaction transaction;
        try {
            session = config.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(client);
            transaction.commit();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteClient(Client client) {
        Session session = null;
        Transaction transaction;
        try {
            session = config.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(client);
            transaction.commit();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


}
