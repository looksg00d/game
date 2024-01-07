package com.example.game00.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class GuessNumberController {
    @FXML
    private TextField numberField;
    @FXML
    private TextArea textArea;

    private int numberToGuess;

    public void initialize() {
        numberToGuess = new Random().nextInt(100) + 1; // Загадываем число от 1 до 100
    }

    @FXML
    private void processGuess() {
        try {
            int guess = Integer.parseInt(numberField.getText());
            // Отправка догадки на сервер
            // Получение и обработка ответа от сервера
        } catch (NumberFormatException e) {
            textArea.appendText("Enter the correct number!\n");
        }
    }


    @FXML
    private void openChat() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/game00/chat.fxml")); // Укажите правильный путь к FXML
            Parent root = loader.load();

            Stage chatStage = new Stage();
            chatStage.setTitle("Chat");
            chatStage.setScene(new Scene(root));
            chatStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибок загрузки FXML
        }
    }
}
