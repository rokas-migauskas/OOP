package com.stream;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String imageLink;
    private String ipAddress;

    public User(String firstName, String lastName, String email, String imageLink, String ipAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageLink = imageLink;
        this.ipAddress = ipAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}

