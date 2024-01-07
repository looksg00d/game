package com.example.game00;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public GameClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void startClient() {
        try (Scanner scanner = new Scanner(System.in)) {
            String inputLine;
            while ((inputLine = scanner.nextLine()) != null) {
                out.println(inputLine);
                System.out.println("Ответ сервера: " + in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GameClient client = new GameClient("localhost", 12345);
        client.startClient();
    }
}