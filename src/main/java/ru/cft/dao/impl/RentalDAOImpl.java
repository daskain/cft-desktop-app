package ru.cft.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.cft.configuration.HibernateConfig;
import ru.cft.dao.RentalDAO;
import ru.cft.model.Rental;

import java.util.List;
import java.util.Optional;

public class RentalDAOImpl implements RentalDAO {

    private HibernateConfig config;

    @Override
    public Optional<Rental> getRentalByContract(String contract) {
        Optional rental;
        Session session = null;

        try {
            session = config.getSessionFactory().openSession();
            rental = session
                    .createQuery("SELECT a FROM Rental a WHERE a.contract = :contract")
                    .setParameter("contract", contract)
                    .uniqueResultOptional();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rental;
    }

    @Override
    public void addNewRental(Rental rental) {
        Session session = null;

        try {
            session = config.getSessionFactory().openSession();
            session.save(rental);
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void editRental(Rental rental) {
        Session session = null;
        Transaction transaction;
        try {
            session = config.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(rental);
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
    public List<Rental> getAllRental() {
        List<Rental> rentalList;
        Session session = null;
        try {
            session = config.getSessionFactory().openSession();
            rentalList = session
                    .createQuery("SELECT a FROM Rental a")
                    .getResultList();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rentalList;
    }
}
