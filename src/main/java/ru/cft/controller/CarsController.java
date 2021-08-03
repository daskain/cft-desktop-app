package ru.cft.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.cft.App;
import ru.cft.model.Car;
import ru.cft.model.Client;
import ru.cft.pojo.Dictionary;
import ru.cft.service.CarsService;

import java.io.IOException;
import java.util.Optional;

public class CarsController {

    private CarsService carsService = new CarsService();
    private ObservableList<Car> cars = null;

    @FXML
    private TextField vinCarField;

    @FXML
    private TableColumn<Car, Button> delete;

    @FXML
    private TextField numberCarField;

    @FXML
    private TableColumn<Car, String> vinCar;

    @FXML
    private TableColumn<Car, String> brandCar;

    @FXML
    private TextField modelCarField;

    @FXML
    private TableColumn<Car, String> modelCar;

    @FXML
    private Button addCarButton;

    @FXML
    private TableView<Car> tableOfCars;

    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<Car, String> statusCar;

    @FXML
    private TextField brandCarField;

    @FXML
    private TableColumn<Car, Button> changeStatus;

    @FXML
    private TableColumn<Car, String> numberCar;

    @FXML
    void addClientButtonAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (vinCarField.getText().trim().isEmpty()) {
            alert.setContentText("Ошибка! Введите VIN");
            alert.show();
        } else if (brandCarField.getText().trim().isEmpty()) {
            alert.setContentText("Ошибка! Введите марку авто");
            alert.show();
        } else if (modelCarField.getText().trim().isEmpty()) {
            alert.setContentText("Ошибка! Введите модель");
            alert.show();
        } else if (numberCarField.getText().trim().isEmpty()) {
            alert.setContentText("Ошибка! Введите гос.номер");
            alert.show();
        } else {
            String vinCar = vinCarField.getText();
            String brandCar = brandCarField.getText();
            String modelCar = modelCarField.getText();
            String numberCar = numberCarField.getText();

            Car car = new Car();
            car.setVinCar(vinCar);
            car.setBrandCar(brandCar);
            car.setModelCar(modelCar);
            car.setNumberCar(numberCar);
            carsService.addNewCar(car);

            updateTable();
        }

    }

    @FXML
    void closeButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        updateTable();
    }

    private void showModalityWindow(String fxml) throws IOException {
        App app = new App();
        app.showModalityWindow(fxml);
    }

    private void changeScene(String fxml) throws IOException {
        App app = new App();
        app.changeScene(fxml);
    }

    private void updateTable() {
        vinCar.setCellValueFactory(new PropertyValueFactory<Car, String>("vinCar"));
        brandCar.setCellValueFactory(new PropertyValueFactory<Car, String>("brandCar"));
        modelCar.setCellValueFactory(new PropertyValueFactory<Car, String>("modelCar"));
        numberCar.setCellValueFactory(new PropertyValueFactory<Car, String>("numberCar"));
        statusCar.setCellValueFactory(cars ->
                new SimpleStringProperty(Dictionary.carRentalStatus.get(cars.getValue().getStatus())));
        changeStatus.setCellFactory(cel -> new TableCell<Car, Button>() {

            private Button button = new Button();

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Car car = getTableView().getItems().get(getIndex());
                    button.setPrefWidth(115);
                    if (car.getStatus().equals("rented")) {
                        button.setVisible(false);
                        button.disableProperty().set(true);
                    } else {
                        if (car.getStatus().equals("free")) {
                            button.setText("Блокировать");
                        } else {
                            button.setText("Активировать");
                        }
                        button.setVisible(true);
                        button.disableProperty().set(false);
                        button.setOnAction(event -> {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Изменение статуса авто");
                            alert.setContentText("Подтвердите действие");
                            Optional<ButtonType> optional = alert.showAndWait();

                            if (optional.get() == ButtonType.OK) {
                                if (car.getStatus().equals("free")) {
                                    car.setStatus("unavailable");
                                } else {
                                    car.setStatus("free");
                                }
                                carsService.editCar(car);
                                updateTable();
                            } else {

                            }

                        });
                    }
                    setGraphic(button);
                }
            }
        });

        delete.setCellFactory(cel -> new TableCell<Car, Button>() {
            private Button button = new Button();

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Car car = getTableView().getItems().get(getIndex());
                    button.setPrefWidth(110);
                    button.setText("Удалить");
                    if (car.getStatus().equals("rented")) {
                        button.disableProperty().set(true);
                    } else {
                        button.disableProperty().set(false);
                        button.visibleProperty().set(false);
                        button.setOnAction(event -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Удалить авто");
                            alert.setContentText("Подтвердите действие");
                            Optional<ButtonType> optional = alert.showAndWait();
                            if (optional.get() == ButtonType.OK) {
                                carsService.deleteCar(car);
                                updateTable();
                            } else {

                            }

                        });
                    }
                    setGraphic(button);
                }
            }
        });

        tableOfCars.setItems(FXCollections.observableArrayList(carsService.getAllCars()));
    }

    private void getListCars() {
        cars = FXCollections.observableArrayList(carsService.getAllCars());
    }
}


