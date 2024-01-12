package com.example.game00.servers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GuessNumberServer {
    private static final int PORT = 12346;
    private ServerSocket serverSocket;
    private Set<ClientHandler> clientHandlers = Collections.synchronizedSet(new HashSet<>());
    private int numberToGuess;
    private boolean isNumberGuessed = false;

    public GuessNumberServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
        numberToGuess = new Random().nextInt(100) + 1;
        System.out.println("Game server running on port " + PORT + ". Predicted number: " + numberToGuess);
    }

    public void startServer() {
        try {
            int clientId = 1;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, "Client " + clientId++);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(String message) {
        synchronized (clientHandlers) {
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.sendMessage(message);
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private String clientId; // Добавляем идентификатор клиента

        public ClientHandler(Socket socket, String clientId) {
            this.clientSocket = socket;
            this.clientId = clientId; // Инициализация идентификатора клиента
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null && !isNumberGuessed) {
                    int guess;
                    try {
                        guess = Integer.parseInt(inputLine);
                    } catch (NumberFormatException e) {
                        sendMessage("Enter the number");
                        continue;
                    }

                    if (guess == numberToGuess) {
                        isNumberGuessed = true;
                        broadcastMessage(clientId + " guessed the number: " + numberToGuess);
                        break;
                    } else if (guess < numberToGuess) {
                        broadcastMessage(clientId + " said " + guess + ". The number is higher.");
                    } else {
                        broadcastMessage(clientId + " said " + guess + ". The number is lower.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clientHandlers.remove(this);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new GuessNumberServer().startServer();
    }
}
