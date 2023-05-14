module com.data {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.data to javafx.fxml;
    exports com.data;
}