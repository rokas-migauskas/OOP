module com.stream {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.stream to javafx.fxml;
    exports com.stream;
}