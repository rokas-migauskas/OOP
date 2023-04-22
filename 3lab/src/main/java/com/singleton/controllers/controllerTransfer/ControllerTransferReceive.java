package com.singleton.controllers.controllerTransfer;

import com.singleton.data.Triangle;
import com.singleton.dto.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ControllerTransferReceive {



    @FXML
    private Button receiveDataButton;

    @FXML
    private TextArea receiveTextArea;

    private Triangle triangle;

    public void setTriangle(Triangle triangle) {
        this.triangle = triangle;
    }
    @FXML
    void receivedData(ActionEvent event) {
        String dataToBeDisplayed = "Area: "+ triangle.getArea() +" Perimeter: "+ triangle.getPerimeter() +" Height: "+ triangle.getHeight();
        receiveTextArea.setWrapText(true);
        receiveTextArea.setText(dataToBeDisplayed);
    }

}