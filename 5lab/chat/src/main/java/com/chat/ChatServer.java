package com.chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ChatServer {
    private static final int PORT = 8080;

    private List<ChatRoom> chatRooms = Collections.synchronizedList(new ArrayList<>());
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    public void createRoom(String roomName) {
        ChatRoom chatRoom = new ChatRoom(roomName);
        chatRooms.add(chatRoom);
    }

    public void joinRoom(User user, String roomName) {
        ChatRoom chatRoom = findChatRoomByName(roomName);
        if (chatRoom != null) {
            chatRoom.addUser(user);
        }
    }

    public void leaveRoom(User user, String roomName) {
        ChatRoom chatRoom = findChatRoomByName(roomName);
        if (chatRoom != null) {
            chatRoom.removeUser(user);
        }
    }

    public void broadcast(User sender, String roomName, String messageContent) {
        ChatRoom chatRoom = findChatRoomByName(roomName);
        if (chatRoom != null) {
            Message message = new Message(sender, messageContent);
            chatRoom.getMessages().add(message);
            for (User user : chatRoom.getUsers()) {
                if (!user.getUsername().equals(sender.getUsername())) {
                    user.getOutput().println(sender.getUsername() + ": " + message.getContent());
                }
            }
        }
    }


    public void privateMessage(User sender, String receiverUsername, String messageContent) {
        User receiver = findUserByUsername(receiverUsername);
        if (receiver != null) {
            Message message = new Message(sender, messageContent);
            receiver.getOutput().println("Private from " + sender.getUsername() + ": " + message.getContent());
        }
    }
    public void saveData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            File dataFile = new File("chat_data.json");
            objectMapper.writeValue(dataFile, convertChatRoomsToData(chatRooms));
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadData() {
        File dataFile = new File("chat_data.json");
        if (dataFile.exists() && dataFile.isFile()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                ChatRoomData[] loadedRooms = objectMapper.readValue(dataFile, ChatRoomData[].class);
                chatRooms = convertDataToChatRooms(Arrays.asList(loadedRooms));
            } catch (IOException e) {
                System.err.println("Error loading data: " + e.getMessage());
            }
        }
    }



    private ChatRoom findChatRoomByName(String roomName) {
        return chatRooms.stream()
                .filter(chatRoom -> chatRoom.getName().equals(roomName))
                .findFirst()
                .orElse(null);
    }

    private User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start(PORT);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                ClientHandler clientHandler = new ClientHandler(clientSocket, this, output);
                new Thread(clientHandler).start();
                users.add(clientHandler.getUser());
            }
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }


    private List<ChatRoomData> convertChatRoomsToData(List<ChatRoom> chatRooms) {
        List<ChatRoomData> chatRoomDataList = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            ChatRoomData chatRoomData = new ChatRoomData(chatRoom.getName(), convertUsersToData(chatRoom.getUsers()), convertMessagesToData(chatRoom.getMessages()));
            chatRoomDataList.add(chatRoomData);
        }
        return chatRoomDataList;
    }

    private List<MessageData> convertMessagesToData(List<Message> messages) {
        List<MessageData> messageDataList = new ArrayList<>();
        for (Message message : messages) {
            MessageData messageData = new MessageData(message.getSender().getUsername(), message.getContent());
            messageDataList.add(messageData);
        }
        return messageDataList;
    }

    private List<ChatRoom> convertDataToChatRooms(List<ChatRoomData> chatRoomDataList) throws IOException {
        List<ChatRoom> chatRooms = new ArrayList<>();
        for (ChatRoomData chatRoomData : chatRoomDataList) {
            ChatRoom chatRoom = new ChatRoom(chatRoomData.getName());
            chatRoom.setUsers(convertDataToUsers(chatRoomData.getUsers()));
            chatRoom.setMessages(convertDataToMessages(chatRoomData.getMessages()));
            chatRooms.add(chatRoom);
        }
        return chatRooms;
    }

    private List<Message> convertDataToMessages(List<MessageData> messageDataList) throws IOException {
        List<Message> messages = new ArrayList<>();
        for (MessageData messageData : messageDataList) {
            User sender = findUserByUsername(messageData.getSenderUsername());
            if (sender == null) {
                sender = new User(messageData.getSenderUsername(), null);
                users.add(sender);
            }
            Message message = new Message(sender, messageData.getContent());
            messages.add(message);
        }
        return messages;
    }


    private List<UserData> convertUsersToData(List<User> users) {
        List<UserData> userDataList = new ArrayList<>();
        for (User user : users) {
            UserData userData = new UserData(user.getUsername());
            userDataList.add(userData);
        }
        return userDataList;
    }


    private List<User> convertDataToUsers(List<UserData> userDataList) throws IOException {
        List<User> users = new ArrayList<>();
        for (UserData userData : userDataList) {
            User user = new User(userData.getUsername(), null); // Setting socket to null
            users.add(user);
        }
        return users;
    }


    public List<User> getUsers() {
        return users;
    }

}