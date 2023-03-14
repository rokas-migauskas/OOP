package com.studentregsys;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GroupController {
    @FXML
    private ListView<StudentGroup> studentGroupListView;
    @FXML
    private TextField groupNameField;
    @FXML
    private Button addGroupButton;
    @FXML
    private Button removeGroupButton;
    @FXML
    private Button backToMenuButton;
    @FXML
    private Button editGroupButton;


    private ObservableList<StudentGroup> studentGroups = FXCollections.observableArrayList();
    private GroupManager groupManager;


    // Default constructor
    public GroupController() {
    }

    // Method to set the groupManager and update the list view
    public void initGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
        if (groupManager != null) {
            studentGroups.setAll(groupManager.getStudentGroups());
            studentGroupListView.setItems(studentGroups);
        }
    }

    public GroupController(GroupManager groupManager) {
        this.groupManager = groupManager;
    }



    public void setGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
        if (groupManager != null) {
            studentGroups.setAll(groupManager.getStudentGroups());
        }
    }

    @FXML
    private void openStudentGroupEditor() {
        StudentGroup selectedGroup = studentGroupListView.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentGroup.fxml"));
                Parent studentGroupRoot = loader.load();
                Scene studentGroupScene = new Scene(studentGroupRoot);

                StudentGroupController studentGroupController = loader.getController();
                studentGroupController.setStudentGroup(selectedGroup);

                Stage stage = (Stage) studentGroupListView.getScene().getWindow();
                stage.setScene(studentGroupScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void editGroup() {
        try {
            // Get the selected group
            StudentGroup selectedGroup = studentGroupListView.getSelectionModel().getSelectedItem();
            if (selectedGroup != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentGroup.fxml"));
                Parent studentGroupRoot = loader.load();
                Scene studentGroupScene = new Scene(studentGroupRoot);

                StudentGroupController studentGroupController = loader.getController();
                studentGroupController.setStudentGroup(selectedGroup);

                Stage stage = (Stage) studentGroupListView.getScene().getWindow();
                stage.setScene(studentGroupScene);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @FXML
    public void initialize() {
        setGroupManager(groupManager);
        studentGroupListView.setItems(studentGroups);
    }

    @FXML
    public void addStudentGroup() {
        String groupName = groupNameField.getText().trim();

        if (!groupName.isEmpty()) {
            StudentGroup newGroup = new StudentGroup(groupName);
            studentGroups.add(newGroup);
            groupManager.addStudentGroup(newGroup);
            groupNameField.clear();
        }
    }

    @FXML
    public void removeStudentGroup() {
        StudentGroup selectedGroup = studentGroupListView.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            studentGroups.remove(selectedGroup);
            groupManager.removeStudentGroup(selectedGroup);
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
