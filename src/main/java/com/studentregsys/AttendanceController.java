package com.studentregsys;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AttendanceController {
    @FXML
    private DatePicker attendanceDatePicker;
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, Boolean> attendanceColumn;
    private StudentGroup studentGroup;

    @FXML
    private Button backToMenuButton;

    private ObservableList<Student> students = FXCollections.observableArrayList();

    public AttendanceController() {

    }
    public AttendanceController(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }
    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("present"));
        attendanceColumn.setCellFactory(CheckBoxTableCell.forTableColumn(attendanceColumn));
        attendanceColumn.setEditable(true);

        studentTableView.setItems(students);
        studentTableView.setEditable(true);
    }

    @FXML
    public void markAttendance() {
        LocalDate attendanceDate = attendanceDatePicker.getValue();
        if (attendanceDate != null) {
            for (Student student : students) {
                boolean isPresent = student.isPresent(); // Assuming you have a `isPresent` property in the Student class
                student.setAttendance(attendanceDate, isPresent);
            }
        }
    }

    @FXML
    public void backToMenu() {
        try {
            Stage stage = (Stage) backToMenuButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent menuRoot = loader.load();
            Scene menuScene = new Scene(menuRoot);
            stage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

