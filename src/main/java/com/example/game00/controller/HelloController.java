package com.example.game00.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button mainButton;

    @FXML
    private Button registerButton; // Добавленная кнопка

    @FXML
    private Text mainText;

    @FXML
    void buttonClick(ActionEvent event) {
        mainText.setText(mainButton.getText());
    }

    @FXML
    void showRegistrationScreen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/game00/registration-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 552, 379);
            Stage stage = new Stage();
            stage.setTitle("Registration");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showLoginScreen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/game00/login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 552, 379);
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
    }
}
