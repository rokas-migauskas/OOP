package com.studentregsys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GroupController implements Initializable{
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

    private DataManager dataManager;
    // Default constructor
    public GroupController() {
        dataManager = DataManager.getInstance();
    }

    // Method to set the groupManager and update the list view
    public void initGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
        populateListView();
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        populateListView();
    }

    public GroupController(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentGroupListView.setItems(studentGroups);

        populateListView();
    }


    public void populateListView() {
        if (groupManager != null) {
            studentGroups.setAll(groupManager.getStudentGroups());
        }
    }

    public void setGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
        if (groupManager != null) {
            studentGroups.setAll(groupManager.getStudentGroups());
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
                studentGroupController.initGroupManager(groupManager);
                studentGroupController.setDataManager(dataManager);

                Stage stage = (Stage) studentGroupListView.getScene().getWindow();
                stage.setScene(studentGroupScene);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }








    @FXML
    public void addStudentGroup() {
        String groupName = groupNameField.getText().trim();

        if (!groupName.isEmpty()) {
            StudentGroup newGroup = new StudentGroup(groupName);
            studentGroups.add(newGroup);
            groupManager.addStudentGroup(newGroup);
            groupNameField.clear();

            System.out.println("Group added: " + newGroup);
            dataManager.setGroupManager(groupManager);
            dataManager.save();
        }
    }

    @FXML
    public void removeStudentGroup() {
        StudentGroup selectedGroup = studentGroupListView.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            studentGroups.remove(selectedGroup);
            groupManager.removeStudentGroup(selectedGroup);

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
