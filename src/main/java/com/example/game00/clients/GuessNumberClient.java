package com.example.game00.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class GuessNumberClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Consumer<String> onMessageReceived;

    public GuessNumberClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // поток для прослушивания ответов от сервера
        new Thread(this::listenForServerMessages).start();
    }

    private void listenForServerMessages() {
        try {
            String fromServer;
            while ((fromServer = in.readLine()) != null) {
                if (onMessageReceived != null) {
                    onMessageReceived.accept(fromServer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Устанавливаем обработчик входящих сообщений
    public void setOnMessageReceived(Consumer<String> onMessageReceived) {
        this.onMessageReceived = onMessageReceived;
    }

    // Отправка числа на сервер
    public void sendGuess(String guess) {
        out.println(guess);
    }

    // Закрытие соединения
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
