package com.studentregsys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;

import com.studentregsys.GroupManager;

public class MainController{
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
    private DataManager dataManager;

    public MainController() {
        if (this.dataManager == null) {
            this.dataManager = DataManager.getInstance();
        }
        if (this.groupManager == null) {
            this.groupManager = new GroupManager();
        }
        if (this.studentGroups == null) {
            this.studentGroups = new ArrayList<>();
        }

    }

    public static void setPrimaryStage(Stage primaryStage) {
        MainController.primaryStage = primaryStage;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }




    public void initGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    public void manageAttendance(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Attendance.fxml"));
            Parent parent = loader.load();
            AttendanceController attendanceController = loader.getController();
            attendanceController.initGroupManager(groupManager);
            attendanceController.setDataManager(dataManager);
            attendanceController.populateGroupComboBox(); // Call this method here

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            window.setTitle("Attendance Manager");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void openGroupManager(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Group.fxml"));
        Parent parent = loader.load();
        GroupController groupController = loader.getController();
        groupController.initGroupManager(groupManager);
        groupController.setDataManager(dataManager);

        groupController.populateListView();
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("Group Manager");
        window.setScene(scene);
        window.show();
    }


    public void updateGroupListView() {
        if (groupManager != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Group.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GroupController groupController = loader.getController();
            groupController.initGroupManager(groupManager);
            groupController.setDataManager(dataManager);
            groupController.populateListView();
        }
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
        dataManager.save();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
