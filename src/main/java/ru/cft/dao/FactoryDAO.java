package ru.cft.dao;

import ru.cft.dao.impl.CarsDAOImpl;
import ru.cft.dao.impl.ClientsDAOImpl;
import ru.cft.dao.impl.RentalDAOImpl;
import ru.cft.dao.impl.UsersDAOImpl;

public class FactoryDAO {

    private static UsersDAO usersDAO = null;
    private static CarsDAO carsDAO = null;
    private static ClientsDAO clientsDAO = null;
    private static RentalDAO rentalDAO = null;
    private static volatile FactoryDAO instance;

    private FactoryDAO() {

    }

    public static FactoryDAO getInstance() {

        if (instance == null) {
            synchronized (FactoryDAO.class) {
                if (instance == null) {
                    instance = new FactoryDAO();
                }
            }
        }

        return instance;
    }

    public UsersDAO getUsersDAO() {
        if (usersDAO == null) {
            usersDAO = new UsersDAOImpl();
        }
        return usersDAO;
    }

    public CarsDAO getCarsDAO() {
        if (carsDAO == null) {
            carsDAO = new CarsDAOImpl();
        }
        return carsDAO;
    }

    public ClientsDAO getClientsDAO() {
        if (clientsDAO == null) {
            clientsDAO = new ClientsDAOImpl();
        }
        return clientsDAO;
    }

    public RentalDAO getRentalDAO() {
        if (rentalDAO == null) {
            rentalDAO = new RentalDAOImpl();
        }
        return rentalDAO;
    }


}
