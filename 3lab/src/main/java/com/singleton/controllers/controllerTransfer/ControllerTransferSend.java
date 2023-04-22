package com.singleton.controllers.controllerTransfer;

import com.singleton.data.Triangle;
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

public class ControllerTransferSend {

    @FXML
    private TextField sideLengthField;

    @FXML
    private Button submitDataButton;

    @FXML
    void dataSubmitted(ActionEvent event) throws IOException {
        Triangle triangle = new Triangle();
        triangle.setSideLength(Double.parseDouble(sideLengthField.getText()));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/singleton/controllers/controllerTransfer/controllerTransferReceive.fxml"));
        loader.setController(new ControllerTransferReceive());
        Parent parent = loader.load();
        ControllerTransferReceive controller = loader.getController();
        controller.setTriangle(triangle);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("ControllerTransferReceive");
        window.setScene(scene);
        window.show();
    }
}
