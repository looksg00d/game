package com.example.game00.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.example.game00.clients.GuessNumberClient;
import javafx.stage.Stage;

import java.io.IOException;

public class GuessNumberController {
    @FXML
    private TextField numberField;
    @FXML
    private TextArea textArea;

    private GuessNumberClient gameClient;

    public void initialize() {
        try {
            // инициализация
            gameClient = new GuessNumberClient("localhost", 12346);
            gameClient.setOnMessageReceived(this::handleServerMessage);
        } catch (IOException e) {
            textArea.appendText("Error connecting to the game server.\n");
        }
    }

    private void handleServerMessage(String message) {
        textArea.appendText(message + "\n");
    }

    @FXML
    private void processGuess() {
        String guess = numberField.getText();
        gameClient.sendGuess(guess); // юзер отправляет догадку
    }

    @FXML
    private void openChat() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/game00/chat.fxml"));
            Parent root = loader.load();
            Stage chatStage = new Stage();
            chatStage.setTitle("Chat");
            chatStage.setScene(new Scene(root));
            chatStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}