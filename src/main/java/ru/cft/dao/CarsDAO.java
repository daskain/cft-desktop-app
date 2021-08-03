package ru.cft.dao;

import ru.cft.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarsDAO {

    List<Car> getAllCars();
    Optional<Car> getCarByVIN(String vinNumber);
    void editCar(Car car);
    void deleteCarById(String vinNumber);
    void deleteCar(Car car);
    void addNewCar(Car car);


}
