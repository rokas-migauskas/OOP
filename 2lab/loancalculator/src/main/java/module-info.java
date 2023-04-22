module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;


    opens gui to javafx.fxml;
    exports gui;
}