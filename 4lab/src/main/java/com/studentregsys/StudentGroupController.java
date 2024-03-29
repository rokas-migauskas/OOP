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
            newStudent.setGroupName(studentGroup.getGroupName());
            students.add(newStudent);

            // Update the student list in the groupManager
            int groupIndex = groupManager.getStudentGroups().indexOf(studentGroup);
            groupManager.getStudentGroups().get(groupIndex).addStudent(newStudent);

            firstNameField.clear();
            lastNameField.clear();
            studentIDField.clear();
            System.out.println("Student added");
            dataManager.setGroupManager(groupManager);
            dataManager.save();
        }
    }

    @FXML
    public void removeStudent() {
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            students.remove(selectedStudent);

            // Update the student list in the groupManager
            int groupIndex = groupManager.getStudentGroups().indexOf(studentGroup);
            groupManager.getStudentGroups().get(groupIndex).removeStudent(selectedStudent);
            dataManager.setGroupManager(groupManager);
            dataManager.save();
        }
    }


    @FXML
    public void backToMenu() {
        if (dataManager != null) {
            dataManager.save();
        } else {
            System.err.println("DataManager is not initialized.");
        }
        try {
            Stage stage = (Stage) backToMenuButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent menuRoot = loader.load();
            Scene menuScene = new Scene(menuRoot);
            MainController mainController = loader.getController();
            mainController.initGroupManager(groupManager);
            mainController.setDataManager(dataManager);

            stage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
