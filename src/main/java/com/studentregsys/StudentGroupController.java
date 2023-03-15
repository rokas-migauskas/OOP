package com.studentregsys;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentGroupController {
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, String> studentIDColumn;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField studentIDField;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Label groupNameLabel;
    @FXML
    private Button backToMenuButton;
    private GroupManager groupManager;


    private ObservableList<Student> students = FXCollections.observableArrayList();
    private StudentGroup studentGroup;
    private DataManager dataManager;

    public StudentGroupController() {
    }

    public void initGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void initData(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
        if (studentGroup != null) {
            students.setAll(studentGroup.getStudents());
            groupNameLabel.setText(studentGroup.getGroupName());
            studentTableView.setItems(students);
        }
    }


    public StudentGroupController(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }
    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
        if (studentGroup != null) {
            students.setAll(studentGroup.getStudents());
            groupNameLabel.setText(studentGroup.getGroupName());
        }
    }


    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));

        studentTableView.setItems(students);
    }

    @FXML
    public void addStudent() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String studentID = studentIDField.getText().trim();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !studentID.isEmpty()) {
            Student newStudent = new Student(firstName, lastName, studentID);
            students.add(newStudent);
            studentGroup.addStudent(newStudent);

            firstNameField.clear();
            lastNameField.clear();
            studentIDField.clear();
        }
    }

    @FXML
    public void removeStudent() {
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            students.remove(selectedStudent);
            studentGroup.removeStudent(selectedStudent);
        }
    }

    @FXML
    public void backToMenu() {
        try {
            Stage stage = (Stage) backToMenuButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent menuRoot = loader.load();
            MainController mainController = loader.getController();
            mainController.setDataManager(dataManager);
            Scene menuScene = new Scene(menuRoot);
            stage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
