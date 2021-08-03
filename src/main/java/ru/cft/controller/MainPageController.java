package ru.cft.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.cft.App;
import ru.cft.service.CarsService;

public class MainPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logOutButton;

    @FXML
    private Button arrangeALeaseButton;

    @FXML
    private Button carsButtonAction;

    @FXML
    void logOut(ActionEvent event) throws IOException {
        changeScene("loginController");
    }

    @FXML
    void leaseButtonAction() throws IOException {
        showModalityWindow("leaseController");
    }

    @FXML
    void carsButtonAction() throws IOException {
        showModalityWindow("carsController");
    }

    @FXML
    void clientsButtonAction() throws IOException {
        showModalityWindow("clientsController");
    }

    private void showModalityWindow(String fxml) throws IOException {
        App app = new App();
        app.showModalityWindow(fxml);
    }

    private void changeScene(String fxml) throws IOException {
        App app = new App();
        app.changeScene(fxml);
    }

}
