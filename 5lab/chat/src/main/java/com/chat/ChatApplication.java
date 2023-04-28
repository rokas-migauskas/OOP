package com.chat;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ChatApplication extends Application {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    private String username;
    private TextArea chatArea;

    @Override
    public void start(Stage primaryStage) {
        // Create GUI components and add them to the scene
        VBox root = new VBox(10);
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);

        HBox inputBox = new HBox(5);
        TextField inputField = new TextField();
        inputField.setPromptText("Enter your message");
        Button sendButton = new Button("Send");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        Button setUsernameButton = new Button("Set Username");

        TextField roomNameField = new TextField();
        roomNameField.setPromptText("Enter room name");
        Button createRoomButton = new Button("Create Room");
        Button joinRoomButton = new Button("Join Room");

        TextField recipientField = new TextField();
        recipientField.setPromptText("Enter recipient's username");
        Button sendPrivateButton = new Button("Send Private");

        HBox userInfo = new HBox(10, usernameField, setUsernameButton);
        HBox roomInfo = new HBox(10, roomNameField, recipientField, createRoomButton, joinRoomButton, sendPrivateButton);
        inputBox.getChildren().addAll(inputField, sendButton);
        inputBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(userInfo, roomInfo, chatArea, inputBox);
        Scene scene = new Scene(root, 600, 300);

        setUsernameButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            if (!username.isEmpty()) {
                this.username = username;
                output.println("set_username:" + username);
                chatArea.appendText("Username set to: " + username + "\n");
            }
        });

        createRoomButton.setOnAction(event -> {
            String roomName = roomNameField.getText().trim();
            if (!roomName.isEmpty()) {
                output.println("create_room:" + roomName);
                chatArea.appendText("Created room: " + roomName + "\n");
                chatArea.appendText("Joined room: " + roomName + "\n");
            }
        });

        joinRoomButton.setOnAction(event -> {
            String roomName = roomNameField.getText().trim();
            if (!roomName.isEmpty()) {
                output.println("join_room:" + roomName);
                chatArea.appendText("Joined room: " + roomName + "\n");
            }
        });

        sendButton.setOnAction(event -> {
            String message = inputField.getText().trim();
            String roomName = roomNameField.getText().trim();
            if (message.equalsIgnoreCase("save")) {
                output.println("save");
                chatArea.appendText("Saving data...\n");
            } else if (message.equalsIgnoreCase("load")) {
                output.println("load");
                chatArea.appendText("Loading data...\n");
            } else if (!message.isEmpty() && !roomName.isEmpty()) {
                output.println("broadcast:" + roomName + ":" + message);
                chatArea.appendText("You: " + message + "\n");
            }
            inputField.clear();
        });

        sendPrivateButton.setOnAction(event -> {
            String message = inputField.getText().trim();
            String recipient = recipientField.getText().trim();
            if (!message.isEmpty() && !recipient.isEmpty()) {
                output.println("private:" + recipient + ":" + message);
                chatArea.appendText("Private to " + recipient + ": " + message + "\n");
                inputField.clear();
            }
        });


        // Set up event listeners for user actions, such as button clicks or text input
        primaryStage.setTitle("Chat Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Connect to the ChatServer and handle incoming messages and commands
        connectToServer();
    }


    private void connectToServer() {
        try {
            socket = new Socket(HOST, PORT);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            startMessageListener();
            output.println("load");
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
        }
    }


    private void startMessageListener() {
        new Thread(() -> {
            try {
                String message;
                while ((message = input.readLine()) != null) {
                    final String finalMessage = message;
                    // Update the TextArea with the received message on the JavaFX application thread
                    Platform.runLater(() -> chatArea.appendText(finalMessage + "\n"));
                }
            } catch (IOException e) {
                System.err.println("Error reading messages from the server: " + e.getMessage());
            }
        }).start();
    }

    private void sendMessage(String message) {
        try {
            output.println(message);
            // Display the sent message in the chat area
            chatArea.appendText("You: " + message + "\n");
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

