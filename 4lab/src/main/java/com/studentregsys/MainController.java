package com.studentregsys;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private DataIO csvIO;
    private DataIO excelIO;

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
        this.csvIO = new CSVIO();
        this.excelIO = new ExcelIO();

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

    @FXML
    private void openReportWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        Parent parent = loader.load();
        ReportController reportController = loader.getController();
        reportController.setDataManager(dataManager);
        reportController.initGroupManager(groupManager);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("Report Generator");
        window.setScene(scene);
        window.show();
    }







    @FXML
    public void exportStudents() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Students");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            String fileName = file.getName();
            if (fileName.endsWith(".csv")) {
                try {
                    csvIO.exportStudents(dataManager.getGroupManager().getAllStudents(), file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (fileName.endsWith(".xlsx")) {
                try {
                    excelIO.exportStudents(dataManager.getGroupManager().getAllStudents(), file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void importStudents() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Students");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            try {
                String fileName = file.getName();
                List<Student> importedStudents;
                if (fileName.endsWith(".csv")) {
                    importedStudents = csvIO.importStudents(file.getAbsolutePath());
                } else if (fileName.endsWith(".xlsx")) {
                    importedStudents = excelIO.importStudents(file.getAbsolutePath());
                } else {
                    return;
                }

                for (Student student : importedStudents) {
                    String groupName = student.getGroupName();
                    StudentGroup studentGroup = null;

                    // Find the group with the same name
                    for (StudentGroup group : groupManager.getStudentGroups()) {
                        if (group.getGroupName().equals(groupName)) {
                            studentGroup = group;
                            break;
                        }
                    }

                    // If the group doesn't exist, create a new group and add it to the group manager
                    if (studentGroup == null) {
                        studentGroup = new StudentGroup(groupName);
                        groupManager.addStudentGroup(studentGroup);
                    }

                    // Add the imported student to the group
                    studentGroup.addStudent(student);
                }
                dataManager.save(); // Save the imported data
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
