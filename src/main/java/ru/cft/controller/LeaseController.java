package ru.cft.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.cft.model.Car;
import ru.cft.model.Client;
import ru.cft.model.Rental;
import ru.cft.model.StatusOfCar;
import ru.cft.service.CarsService;
import ru.cft.service.ClientsService;
import ru.cft.service.RentalService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LeaseController {

    private CarsService carsService = new CarsService();
    private ClientsService clientsService = new ClientsService();
    private RentalService rentalService = new RentalService();

    @FXML
    private TableView<Rental> tableOfLeaseInfo;

    @FXML
    private Button addLeaseButton;

    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<Rental, String> timeRent;

    @FXML
    private TableColumn<Rental, String> clientInfo;

    @FXML
    private TableColumn<Rental, Button> closeContract;

    @FXML
    private TableColumn<Rental, String> carInfo;

    @FXML
    private TableColumn<Rental, String> lease;

    @FXML
    private ComboBox<String> selectCarBox;

    @FXML
    private ComboBox<String> selectClientBox;

    @FXML
    void initialize() {

        updateTable();
    }

    @FXML
    void addLeaseButtonAction() {
        Rental rental = new Rental();
        System.out.println();
        Car car = carsService.getCarByVIN(getVin(selectCarBox.getValue())).get();
        Client client = clientsService.getClientByPassport(getPassport(selectClientBox.getValue())).get();
        UUID uuid = UUID.randomUUID();
        rental.setCar(car);
        rental.setClient(client);
        rental.setContract(uuid.toString());
        rental.setStartTimeRental(new Timestamp(System.currentTimeMillis()));
        rental.setActive(true);
        rentalService.addNewRental(rental);
        car.setStatus(StatusOfCar.RENTED.getStatus());
        carsService.editCar(car);
        updateTable();
    }

    @FXML
    void selectCar() {
    }

    @FXML
    void selectClient() {
    }

    @FXML
    void closeButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private String getCarInfo(Car car) {
        StringBuilder carInfo = new StringBuilder();
        carInfo.append(car.getVinCar());
        carInfo.append(" ");
        carInfo.append(car.getBrandCar());
        carInfo.append(" ");
        carInfo.append(car.getModelCar());
        carInfo.append(" ");
        carInfo.append(car.getNumberCar());
        return carInfo.toString();
    }

    private String getClientInfo(Client client) {
        StringBuilder clientInfo = new StringBuilder();
        clientInfo.append(client.getPassport());
        clientInfo.append(" ");
        clientInfo.append(client.getLastName());
        clientInfo.append(" ");
        clientInfo.append(client.getFirstName());
        clientInfo.append(" ");
        clientInfo.append(client.getPatronymic());
        return clientInfo.toString();
    }

    private void updateTable() {
        insertCarsInfoListToCombBox();
        insertClientsInfoListToCombBox();

        lease.setCellValueFactory(cel -> new ReadOnlyObjectWrapper(cel.getValue().getContract()));
        clientInfo.setCellValueFactory(cel -> new ReadOnlyObjectWrapper(
                getClientInfo(clientsService.getClientByPassport(cel.getValue().getClient().getPassport()).get())));
        carInfo.setCellValueFactory(cel -> new ReadOnlyObjectWrapper(
                getCarInfo(carsService.getCarByVIN(cel.getValue().getCar().getVinCar()).get())));
        timeRent.setCellValueFactory(cel -> new ReadOnlyObjectWrapper(getCurrentTimeContract(cel.getValue().getStartTimeRental())));

        closeContract.setCellFactory(cel -> new TableCell<Rental, Button>() {

            private Button button = new Button();

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Rental rental = getTableView().getItems().get(getIndex());
                    button.setPrefWidth(103);
                    if (rental.isActive()) {
                        button.setText("Завершить");
                    } else {
                        button.disableProperty().set(true);
                        button.setVisible(false);
                    }

                    button.setOnAction(event -> {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Завершить аренду?");
                        alert.setContentText("Подтвердите действие");
                        Optional<ButtonType> optional = alert.showAndWait();

                        if (optional.get() == ButtonType.OK) {
                            rental.setActive(!rental.isActive());
                            rentalService.editRental(rental);
                            Car car = rental.getCar();
                            car.setStatus(StatusOfCar.FREE.getStatus());
                            carsService.editCar(car);
                            updateTable();
                        } else {

                        }

                    });
                    setGraphic(button);
                }
            }
        });
        tableOfLeaseInfo.setItems(FXCollections.observableArrayList(rentalService.getAllRental()));

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

    private String getCurrentTimeContract(Timestamp timestamp) {
        long diffInMillies = new Timestamp(System.currentTimeMillis()).getTime() - timestamp.getTime();
        long minuteDifference = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return Long.toString(minuteDifference);
    }

}
