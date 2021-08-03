package ru.cft.service;

import ru.cft.dao.FactoryDAO;
import ru.cft.dao.RentalDAO;
import ru.cft.model.Rental;

import java.util.List;
import java.util.Optional;

public class RentalService {

    public Optional<Rental> getRentalByContract(String contract) {
        Optional<Rental> rental = getRentalDAO().getRentalByContract(contract);
        return rental;
    }
    public void addNewRental(Rental rental) {
        getRentalDAO().addNewRental(rental);
    }

    public List<Rental> getAllRental() {
        return getRentalDAO().getAllRental();
    }

    public void editRental(Rental rental) {
        getRentalDAO().editRental(rental);
    }

    private RentalDAO getRentalDAO() {
        return FactoryDAO.getInstance().getRentalDAO();
    }


}
