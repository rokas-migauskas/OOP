module com.singleton {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.singleton to javafx.fxml;
    opens com.singleton.controllers to javafx.fxml;
    opens com.singleton.controllers.singleton;
    opens com.singleton.controllers.controllerTransfer;
    opens com.singleton.controllers.getUserData;

    exports com.singleton;
    exports com.singleton.controllers;
    exports com.singleton.controllers.singleton;
    exports com.singleton.controllers.getUserData;
    exports com.singleton.controllers.controllerTransfer;
}