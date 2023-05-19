module com.game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires org.antlr.antlr4.runtime;

    opens com.game to javafx.fxml;
    exports com.game;
}