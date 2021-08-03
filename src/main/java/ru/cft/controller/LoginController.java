package ru.cft.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.cft.App;
import ru.cft.service.UsersService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    private UsersService usersService = new UsersService();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passField;

    @FXML
    private Button authButton;

    @FXML
    private TextField loginField;

    @FXML
    private Label wrongCredField;

    public void authButtonClick(ActionEvent event) throws IOException {
        checkCredentials();
    }

    private void checkCredentials() throws IOException {

        App app = new App();
        String login = loginField.getText();
        String pass = passField.getText();

        if (login.equals("") & pass.equals("")) {
            wrongCredField.setText("Введите имя пользователя и пароль!");
        } else if (login.equals("")) {
            wrongCredField.setText("Введите имя пользователя!");
        } else if (pass.equals("")) {
            wrongCredField.setText("Введите пароль!");
        } else if (!usersService.checkCredInDB(login, pass)) {
            wrongCredField.setText("Неверный логин/пароль");
        } else {
            wrongCredField.setText("Успех");
            app.changeScene("mainPageController");
        }

    }
}
