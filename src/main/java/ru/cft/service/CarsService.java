package ru.cft.service;

import ru.cft.dao.CarsDAO;
import ru.cft.dao.FactoryDAO;
import ru.cft.model.Car;

import java.util.List;
import java.util.Optional;

public class CarsService {

    public List<Car> getAllCars() {

        List<Car> cars = getCarsDAO().getAllCars();
        return cars;
    }

    public Optional<Car> getCarByVIN(String vinNumber) {
        Optional<Car> car = getCarsDAO().getCarByVIN(vinNumber);
        return car;
    }

    public void editCar(Car car) {
        getCarsDAO().editCar(car);
    }

    public void addNewCar(Car car) {
        getCarsDAO().addNewCar(car);
    }

    public void deleteCar(Car car) {
        getCarsDAO().deleteCar(car);
    }

    private CarsDAO getCarsDAO() {
        return FactoryDAO.getInstance().getCarsDAO();
    }
}
