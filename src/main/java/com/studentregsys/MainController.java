package com.studentregsys;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;


public class MainController {
    @FXML
    private Button groupManagerButton;

    private GroupManager groupManager;

    @FXML
    private Button manageStudentGroupsButton;

    @FXML
    private Button manageAttendanceButton;

    @FXML
    private Button exportStudentsButton;

    @FXML
    private Button importStudentsButton;

    @FXML
    private Button exitButton;

    private StudentGroup studentGroup;

    private List<StudentGroup> studentGroups;
    private static Stage primaryStage;

    public MainController() {
        this.groupManager = new GroupManager();
        this.studentGroups = new ArrayList<>();
    }
    public static void setPrimaryStage(Stage primaryStage) {
        MainController.primaryStage = primaryStage;
    }

    @FXML
    public void manageStudentGroups(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("StudentGroup.fxml"));
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("Group Manager");
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void manageAttendance(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Attendance.fxml"));
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("Attendance Manager");
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void openGroupManager(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Group.fxml"));
        Parent parent = loader.load();
        GroupController groupController = loader.getController();
        groupController.initGroupManager(groupManager);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("Group Manager");
        window.setScene(scene);
        window.show();
    }


    @FXML
    public void exportStudents() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Students Data");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            String fileName = selectedFile.getAbsolutePath();
            try {
                if (fileName.endsWith(".csv")) {
                    CSVDataExport csvDataExport = new CSVDataExport();
                    csvDataExport.exportStudents(studentGroup.getStudents(), fileName);
                } else if (fileName.endsWith(".xlsx")) {
                    ExcelDataExport excelDataExport = new ExcelDataExport();
                    excelDataExport.exportStudents(studentGroup.getStudents(), fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void importStudents() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Students Data");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            String fileName = selectedFile.getAbsolutePath();
            try {
                if (fileName.endsWith(".csv")) {
                    CSVDataExport csvDataExport = new CSVDataExport();
                    List<Student> importedStudents = csvDataExport.importStudents(fileName);
                    studentGroup.getStudents().addAll(importedStudents);
                } else if (fileName.endsWith(".xlsx")) {
                    ExcelDataExport excelDataExport = new ExcelDataExport();
                    List<Student> importedStudents = excelDataExport.importStudents(fileName);
                    studentGroup.getStudents().addAll(importedStudents);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void exit() {
        primaryStage.close();
    }
}
