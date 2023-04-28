package com.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private String name;
    private List<User> users;
    private List<Message> messages;

    public ChatRoom(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
