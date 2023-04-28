package com.chat;

import java.io.Serializable;

public class UserData implements Serializable {
    private String username;

    public UserData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
