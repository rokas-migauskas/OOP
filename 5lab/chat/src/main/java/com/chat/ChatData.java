package com.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatData {
    private List<Message> messages;
    private List<ChatRoomData> chatRooms;
    private List<UserData> users;

    public ChatData() {
        this.messages = new ArrayList<>();
        this.chatRooms = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<ChatRoomData> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(List<ChatRoomData> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public List<UserData> getUsers() {
        return users;
    }

    public void setUsers(List<UserData> users) {
        this.users = users;
    }
}
