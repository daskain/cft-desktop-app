package ru.cft.service;

import ru.cft.dao.CarsDAO;
import ru.cft.dao.FactoryDAO;
import ru.cft.dao.UsersDAO;
import ru.cft.model.Car;
import ru.cft.model.User;

import java.util.Optional;

public class UsersService {


    public boolean checkCredInDB(String login, String pass) {

        Optional<User> user = FactoryDAO.getInstance().getUsersDAO().getUserByLogin(login);
        return (!user.isEmpty() && user.get().getPassword().equals(pass)) ? true : false;
    }


}
