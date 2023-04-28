module com.chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;


    opens com.chat to javafx.fxml;
    exports com.chat;
}