package com.singleton.controllers.singleton;

import com.singleton.data.Triangle;
import com.singleton.dto.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class SingletonReceive {

    Triangle triangle = Singleton.getInstance().getTriangle();

    @FXML
    private Button receiveDataButton;

    @FXML
    private TextArea receiveTextArea;

    @FXML
    void receivedData(ActionEvent event) {
        String dataToBeDisplayed = "Area: "+ triangle.getArea() +" Perimeter: "+ triangle.getPerimeter() +" Height: "+ triangle.getHeight();
        receiveTextArea.setWrapText(true);
        receiveTextArea.setText(dataToBeDisplayed);
    }

}
