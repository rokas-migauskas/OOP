package com.singleton.controllers.getUserData;

import com.singleton.data.Triangle;
import com.singleton.dto.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GetUserDataSend {

    @FXML
    private TextField sideLengthField;

    @FXML
    private Button submitDataButton;

    @FXML
    void dataSubmitted(ActionEvent event) throws IOException {
        Triangle triangle = new Triangle();
        triangle.setSideLength(Double.parseDouble(sideLengthField.getText()));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/singleton/controllers/getUserData/getUserDataReceive.fxml"));
        Parent parent = loader.load();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        scene.setUserData(triangle);
        window.setTitle("GetUserData Receive");
        window.setScene(scene);
        window.show();
    }

}