package com.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomData implements Serializable {
    private String name;
    private List<UserData> users;
    private List<MessageData> messages;

    public ChatRoomData(String name, List<UserData> users, List<MessageData> messages) {
        this.name = name;
        this.users = users;
        this.messages = messages;
    }

    public List<MessageData> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageData> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserData> getUsers() {
        return users;
    }

    public void setUsers(List<UserData> users) {
        this.users = users;
    }
}
