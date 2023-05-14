package com.data;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty email;
    private SimpleStringProperty imageLink;
    private SimpleStringProperty ipAddress;

    public Person(String dataLine) {
        String[] data = dataLine.split(";");
        this.firstName = new SimpleStringProperty(data[0]);
        this.lastName = new SimpleStringProperty(data[1]);
        this.email = new SimpleStringProperty(data[2]);
        this.imageLink = new SimpleStringProperty(data[3]);
        this.ipAddress = new SimpleStringProperty(data[4]);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", email=" + email +
                ", imageLink=" + imageLink +
                ", ipAddress=" + ipAddress +
                '}';
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getImageLink() {
        return imageLink.get();
    }

    public SimpleStringProperty imageLinkProperty() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink.set(imageLink);
    }

    public String getIpAddress() {
        return ipAddress.get();
    }

    public SimpleStringProperty ipAddressProperty() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress.set(ipAddress);
    }
}
