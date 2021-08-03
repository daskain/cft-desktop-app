package ru.cft.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.cft.configuration.HibernateConfig;
import ru.cft.dao.CarsDAO;
import ru.cft.model.Car;
import ru.cft.model.Rental;

import java.util.List;
import java.util.Optional;

public class CarsDAOImpl implements CarsDAO {

    private HibernateConfig config;

    @Override
    public List<Car> getAllCars() {

        List<Car> carList;
        Session session = null;
        try {
            session = config.getSessionFactory().openSession();
            carList = session
                    .createQuery("SELECT a FROM Car a", Car.class)
                    .getResultList();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return carList;
    }

    @Override
    public Optional<Car> getCarByVIN(String vinNumber) {

        Optional<Car> car;
        Session session = null;
        try {
            session = config.getSessionFactory().openSession();
            car = session
                    .createQuery("SELECT a FROM Car a WHERE a.vinCar = :vinNumber", Car.class)
                    .setParameter("vinNumber", vinNumber)
                    .uniqueResultOptional();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return car;
    }

    @Override
    public void editCar(Car car) {

        Session session = null;
        Transaction transaction;
        try {
            session = config.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(car);
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
    public void deleteCarById(String vinNumber) {

        Session session = null;
        try {
            session = config.getSessionFactory().openSession();
            session.createQuery("DELETE FROM Car a WHERE a.vinCar = :vinNumber")
                    .setParameter("vinNumber", vinNumber);
            session.flush();

        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteCar(Car car) {

        Session session = null;
        Transaction transaction;
        try {
            session = config.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(car);
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
    public void addNewCar(Car car) {
        Session session = null;
        Transaction transaction;
        try {
            session = config.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(car);
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
