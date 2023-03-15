module com.studentregsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires layout;
    requires kernel;


    opens com.studentregsys to javafx.fxml, com.fasterxml.jackson.databind;

    exports com.studentregsys;
}