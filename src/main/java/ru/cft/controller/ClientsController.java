package ru.cft.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.cft.App;
import ru.cft.model.Client;
import ru.cft.pojo.Dictionary;
import ru.cft.service.ClientsService;

public class ClientsController {

    private ClientsService clientsService = new ClientsService();
    private ObservableList<Client> clients = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addClientButton;

    @FXML
    private Button closeButton;

    @FXML
    private TableView<Client> tableOfClients;

    @FXML
    private TableColumn<Client, String> passport;

    @FXML
    private TableColumn<Client, String> lastName;

    @FXML
    private TableColumn<Client, String> firstName;

    @FXML
    private TableColumn<Client, String> patronymic;

    @FXML
    private TableColumn<Client, Date> birthday;

    @FXML
    private TableColumn<Client, String> isActive;

    @FXML
    private TableColumn<Client, Button> changeStatus;

    @FXML
    private TableColumn<Client, Button> delete;

    @FXML
    private TextField passportField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField patronymicField;


    @FXML
    private DatePicker birthdayField;

    @FXML
    void addClientButtonAction() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (passportField.getText().trim().isEmpty()) {
            alert.setContentText("Ошибка! Введите номер пасспорта");
            alert.show();
        } else if (lastNameField.getText().trim().isEmpty()) {
            alert.setContentText("Ошибка! Введите фамилию");
            alert.show();
        } else if (firstNameField.getText().trim().isEmpty()) {
            alert.setContentText("Ошибка! Введите имя");
            alert.show();
        } else if (birthdayField.getValue() == null) {
            alert.setContentText("Ошибка! Введите дату рождения!");
            alert.show();
        } else {
            String passport = passportField.getText();
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            String patronymic = patronymicField.getText();
            Date birthday = Date.valueOf(birthdayField.getValue());

            Client client = new Client();
            client.setPassport(passport);
            client.setLastName(lastName);
            client.setFirstName(firstName);
            if (passportField.getText().trim().isEmpty()) {
                client.setPatronymic("");
            } else {
                client.setPatronymic(patronymic);
            }
            client.setBirthday(birthday);
            client.setActive(true);
            clientsService.addNewClient(client);
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

    private void getListClients() {
        clients = FXCollections.observableArrayList(clientsService.getAllClients());
    }

    private void deleteClient(String passport) {
        clientsService.deleteClientByPassport(passport);

    }

    private Client getClient(String passport) {
        return clientsService.getClientByPassport(passport).get();
    }

    private void editClient(Client client) {
        clientsService.editClient(client);
    }

    private void updateTable() {
        passport.setCellValueFactory(new PropertyValueFactory<Client, String>("passport"));
        firstName.setCellValueFactory(new PropertyValueFactory<Client, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Client, String>("lastName"));
        patronymic.setCellValueFactory(new PropertyValueFactory<Client, String>("patronymic"));
        birthday.setCellValueFactory(new PropertyValueFactory<Client, Date>("birthday"));
        isActive.setCellValueFactory(clients ->
                new SimpleStringProperty(Dictionary.clientStatus.get(clients.getValue().isActive())));
        changeStatus.setCellFactory(cel -> new TableCell<Client, Button>() {

            private Button button = new Button();

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Client client = getTableView().getItems().get(getIndex());
                    button.setPrefWidth(103);
                    if (client.isActive()) {
                        button.setText("Блокировать");
                    } else {
                        button.setText("Активировать");
                    }

                    button.setOnAction(event -> {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Изменение статуса активности пользователя");
                        alert.setContentText("Подтвердите действие");
                        Optional<ButtonType> optional = alert.showAndWait();

                        if (optional.get() == ButtonType.OK) {
                            client.setActive(!client.isActive());
                            clientsService.editClient(client);
                            updateTable();
                        } else {

                        }

                    });
                    setGraphic(button);
                }
            }
        });

        delete.setCellFactory(cel -> new TableCell<Client, Button>() {
            private Button button = new Button();

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Client client = getTableView().getItems().get(getIndex());
                    button.setPrefWidth(103);
                    button.setText("Удалить");
                    button.setOnAction(event -> {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Удалить пользователя");
                        alert.setContentText("Подтвердите действие");
                        Optional<ButtonType> optional = alert.showAndWait();
                        if (optional.get() == ButtonType.OK) {
                            clientsService.deleteClient(client);
                            updateTable();
                        } else {

                        }

                    });
                    setGraphic(button);
                }
            }
        });


        tableOfClients.setItems(FXCollections.observableArrayList(clientsService.getAllClients()));

    }


}
