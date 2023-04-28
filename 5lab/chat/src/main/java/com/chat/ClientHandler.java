package com.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ChatServer server;
    private User user;

    public ClientHandler(Socket clientSocket, ChatServer server, PrintWriter output) throws IOException {
        this.clientSocket = clientSocket;
        this.server = server;
        this.user = new User("", clientSocket);
    }


    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = input.readLine()) != null) {
                String[] parts = message.split(":", 2);
                String command = parts[0];

                switch (command) {
                    case "set_username":
                        user.setUsername(parts[1]);
                        break;
                    case "create_room":
                        server.createRoom(parts[1]);
                        server.joinRoom(user, parts[1]);
                        break;
                    case "join_room":
                        server.joinRoom(user, parts[1]);
                        break;
                    case "leave_room":
                        server.leaveRoom(user, parts[1]);
                        break;
                    case "broadcast":
                        String[] messageParts = parts[1].split(":", 2);
                        server.broadcast(user, messageParts[0], messageParts[1]);
                        break;
                    case "private":
                        String[] privateParts = parts[1].split(":", 2);
                        server.privateMessage(user, privateParts[0], privateParts[1]);
                        break;
                    case "save":
                        server.saveData();
                        user.getOutput().println("Data saved successfully.");
                        break;
                    case "load":
                        server.loadData();
                        user.getOutput().println("Data loaded successfully.");
                        break;
                    default:
                        // Handle unknown commands or just ignore them
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client communication: " + e.getMessage());
        }
    }

    public User getUser() {
        return user;
    }


}
