package com.example.game00.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ChatClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // создание потока для прослушивания сообщений от сервера
        new Thread(this::listenForServerMessages).start();
    }

    // метод для прослушивания сообщений от сервера
    private void listenForServerMessages() {
        try {
            String fromServer;
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Message from the server: " + fromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // метод для отправки сообщений на сервер
    private void startClient() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите сообщение: ");
            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                out.println(inputLine);
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ChatClient client = new ChatClient("localhost", 12345);
        client.startClient();
    }
}