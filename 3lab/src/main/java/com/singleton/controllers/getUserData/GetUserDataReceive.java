package com.singleton.controllers.getUserData;

import com.singleton.data.Triangle;
import com.singleton.dto.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class GetUserDataReceive {



    @FXML
    private Button receiveDataButton;

    @FXML
    private TextArea receiveTextArea;

    @FXML
    void receivedData(ActionEvent event) {
        Scene currentScene = receiveDataButton.getScene();
        Triangle triangle = (Triangle) currentScene.getUserData();
        String dataToBeDisplayed = "Area: "+ triangle.getArea() +" Perimeter: "+ triangle.getPerimeter() +" Height: "+ triangle.getHeight();
        receiveTextArea.setWrapText(true);
        receiveTextArea.setText(dataToBeDisplayed);
    }

}
