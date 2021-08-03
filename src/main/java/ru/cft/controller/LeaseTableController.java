package ru.cft.controller;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ru.cft.model.Car;
import ru.cft.model.Client;
import ru.cft.model.Rental;
import ru.cft.model.StatusOfCar;
import ru.cft.service.CarsService;
import ru.cft.service.ClientsService;
import ru.cft.service.RentalService;

public class LeaseTableController {

    private CarsService carsService = new CarsService();
    private ClientsService clientsService = new ClientsService();
    private RentalService rentalService = new RentalService();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button confirmLeaseButton;

    @FXML
    private ComboBox<String> selectCarBox;

    @FXML
    private ComboBox<String> selectClientBox;

    @FXML
    private Button closeFrameButton;

    @FXML
    void confirmLease() {
        Rental rental = new Rental();
        System.out.println();
        Car car = carsService.getCarByVIN(getVin(selectCarBox.getValue())).get();
        Client client = clientsService.getClientByPassport(getPassport(selectClientBox.getValue())).get();
        UUID uuid = UUID.randomUUID();
        rental.setCar(car);
        rental.setClient(client);
        rental.setContract(uuid.toString());
        rental.setStartTimeRental(new Timestamp(System.currentTimeMillis()));
        rentalService.addNewRental(rental);

        car.setStatus(StatusOfCar.RENTED.getStatus());
        carsService.editCar(car);
        System.out.println(car.getStatus());
        closeFrame();

    }

    @FXML
    private void selectCar() {

    }

    @FXML
    private void selectClient() {
        List<Client> clientList = clientsService.getAllClients();
    }

    @FXML
    private void closeFrame() {
        Stage stage = (Stage) closeFrameButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        insertCarsInfoListToCombBox();
        insertClientsInfoListToCombBox();
    }

    private List<String> getCarsInfo(List<Car> carList) {

        List<String> carInfoList = carList.stream()
                .filter(x -> x.getStatus().equals("free"))
                .map(s -> {
                    StringBuilder carInfo = new StringBuilder();
                    carInfo.append(s.getVinCar());
                    carInfo.append(" ");
                    carInfo.append(s.getBrandCar());
                    carInfo.append(" ");
                    carInfo.append(s.getModelCar());
                    carInfo.append(" ");
                    carInfo.append(s.getNumberCar());
                    return carInfo.toString();
                })
                .collect(Collectors.toList());

        return carInfoList;
    }

    private void insertCarsInfoListToCombBox() {
        List<Car> carList = carsService.getAllCars();
        List<String> carInfoList = getCarsInfo(carList);
        selectCarBox.getItems().addAll(FXCollections.observableArrayList(carInfoList));
    }

    private List<String> getClientInfo(List<Client> clientList) {
        List<String> clientInfoList = clientList.stream()
                .filter(x -> x.isActive())
                .map(s -> {
                    StringBuilder clientInfo = new StringBuilder();
                    clientInfo.append(s.getPassport());
                    clientInfo.append(" ");
                    clientInfo.append(s.getLastName());
                    clientInfo.append(" ");
                    clientInfo.append(s.getFirstName());
                    clientInfo.append(" ");
                    clientInfo.append(s.getPatronymic());
                    return clientInfo.toString();
                })
                .collect(Collectors.toList());

        return clientInfoList;
    }

    private void insertClientsInfoListToCombBox() {
        List<Client> clientList = clientsService.getAllClients();
        List<String> clientInfoList = getClientInfo(clientList);
        selectClientBox.getItems().addAll(FXCollections.observableArrayList(clientInfoList));
    }

    private String getVin(String sourceString) {

        String[] carInfo = sourceString.trim().split(" ");
        String vin = carInfo[0];
        return vin;
    }

    private String getPassport(String sourceString) {

        String[] clientInfo = sourceString.trim().split(" ");
        String passport = clientInfo[0] + " " + clientInfo[1];
        return passport;
    }

}
