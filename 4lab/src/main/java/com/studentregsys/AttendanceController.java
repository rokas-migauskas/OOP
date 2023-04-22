package com.studentregsys;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AttendanceController {

    private GroupManager groupManager;
    private DataManager dataManager;

    @FXML
    private ComboBox<StudentGroup> groupComboBox;
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

    public AttendanceController(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    public AttendanceController(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }


    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // Custom cell factory for attendance column
        attendanceColumn.setCellFactory(column -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    Student student = getTableRow().getItem();
                    if (student != null) {
                        LocalDate selectedDate = attendanceDatePicker.getValue();
                        student.setPresent(selectedDate, checkBox.isSelected());
                    }
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Student student = getTableView().getItems().get(getIndex());
                    LocalDate selectedDate = attendanceDatePicker.getValue();
                    checkBox.setSelected(student.isPresent(selectedDate));
                    setGraphic(checkBox);
                }
            }
        });

        studentTableView.setItems(students);
        studentTableView.setEditable(true);

        // Add a listener to refresh the TableView when the date changes
        attendanceDatePicker.setOnAction(event -> {
            studentTableView.refresh();
        });
    }


    public void populateGroupComboBox() {
        if (dataManager != null && groupManager != null) {
            groupComboBox.setItems(FXCollections.observableArrayList(groupManager.getStudentGroups()));
            groupComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    studentGroup = newValue;
                    students.clear();
                    students.addAll(studentGroup.getStudents());
                    studentTableView.refresh(); // Add this line to refresh the TableView
                }
            });
        }
    }



    @FXML
    public void markAttendance() {
        LocalDate attendanceDate = attendanceDatePicker.getValue();
        if (attendanceDate != null && studentGroup != null) {
            for (Student student : students) {
                boolean isPresent = student.isPresent(attendanceDate); // Pass attendanceDate as an argument
                student.setAttendance(attendanceDate, isPresent);
            }
        }
    }

    public void initGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
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

