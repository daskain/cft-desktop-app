package ru.cft.dao;

import ru.cft.model.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalDAO {

    Optional<Rental> getRentalByContract(String contract);
    void addNewRental(Rental rental);
    void editRental(Rental rental);
    List<Rental> getAllRental();
}
