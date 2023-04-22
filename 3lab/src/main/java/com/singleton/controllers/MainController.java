package com.singleton.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button controllerTransferButton;

    @FXML
    private Button getUserDataButton;

    @FXML
    private Button singletonButton;

    @FXML
    void goToControllerTransfer(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/singleton/controllers/controllerTransfer/ControllerTransferSend.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("ControllerTransfer Send");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void goToGetUserData(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("/com/singleton/controllers/getUserData/getUserDataSend.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("GetUserData Send");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void goToSingleton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/singleton/controllers/singleton/singletonDataSend.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("Singleton Send");
        window.setScene(scene);
        window.show();
    }

}
