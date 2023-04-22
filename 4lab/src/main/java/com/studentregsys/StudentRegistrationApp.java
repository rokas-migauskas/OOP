package com.studentregsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentRegistrationApp extends Application {

    private GroupManager groupManager;

    public StudentRegistrationApp() {
        groupManager = new GroupManager();
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        DataManager dataManager = DataManager.getInstance();
        dataManager.load();
        GroupManager groupManager = dataManager.getGroupManager();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.initGroupManager(groupManager);
        mainController.setDataManager(dataManager);
        mainController.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Student Registration System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
