package com.example.game00.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatController {

    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    @FXML
    public void initialize() {
        try {
            socket = new Socket("localhost", 12345); // Замените на актуальный адрес и порт сервера
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    String messageFromServer;
                    while ((messageFromServer = in.readLine()) != null) {
                        final String message = messageFromServer;
                        chatArea.appendText("Server: " + message + "\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            chatArea.appendText("You: " + message + "\n");
            out.println(message); // Отправка сообщения на сервер
            messageField.clear();
        }
    }
}
