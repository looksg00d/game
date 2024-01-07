package com.example.game00.controller;

import com.example.game00.database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?")) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) {
                    try {
                        // Загрузка FXML для игры
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/game00/guess-view.fxml")); // Укажите правильный путь к FXML
                        Parent root = loader.load();

                        // Открытие нового окна с игрой
                        Stage stage = new Stage();
                        stage.setTitle("Guess a number");
                        stage.setScene(new Scene(root));
                        stage.show();

                        // Закрытие текущего окна логина
                        ((Stage) usernameField.getScene().getWindow()).close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Обработка ошибок загрузки FXML
                    }
                } else {
                    Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Login ERROR", "Wrong password!"));
                }
            } else {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Login ERROR", "User not found!"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Login ERROR", "An error occurred while logging in"));
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
