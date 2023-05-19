package com.stream;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main extends Application {

    private TableView<User> table = new TableView<>();
    private ObservableList<User> data;
    private FilteredList<User> filteredData;

    private TextField firstNameFilter = new TextField();
    private TextField lastNameFilter = new TextField();
    private TextField emailFilter = new TextField();
    private TextField imageLinkFilter = new TextField();
    private TextField ipAddressFilter = new TextField();

    private Label recordCount = new Label();

    private boolean firstNameSortAscending = true;
    private boolean lastNameSortAscending = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("CSV Data");

        TableColumn<User, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameFilter.setPromptText("Filter by First Name");

        TableColumn<User, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameFilter.setPromptText("Filter by Last Name");

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailFilter.setPromptText("Filter by Email");

        TableColumn<User, String> imageLinkCol = new TableColumn<>("Image Link");
        imageLinkCol.setCellValueFactory(new PropertyValueFactory<>("imageLink"));
        imageLinkFilter.setPromptText("Filter by Image Link");

        TableColumn<User, String> ipAddressCol = new TableColumn<>("IP Address");
        ipAddressCol.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        ipAddressFilter.setPromptText("Filter by IP Address");

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, imageLinkCol, ipAddressCol);

        data = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(data, p -> true);

        firstNameFilter.textProperty().addListener((observable, oldValue, newValue) -> filterTableData());
        lastNameFilter.textProperty().addListener((observable, oldValue, newValue) -> filterTableData());
        emailFilter.textProperty().addListener((observable, oldValue, newValue) -> filterTableData());
        imageLinkFilter.textProperty().addListener((observable, oldValue, newValue) -> filterTableData());
        ipAddressFilter.textProperty().addListener((observable, oldValue, newValue) -> filterTableData());

        recordCount.textProperty().bind(Bindings.size(filteredData).asString());

        Button toUpperCaseButton = new Button("Change to Upper Case");
        toUpperCaseButton.setOnAction(event -> changeCase(true));

        Button toLowerCaseButton = new Button("Change to Lower Case");
        toLowerCaseButton.setOnAction(event -> changeCase(false));

        Button sortFirstNameButton = new Button("Sort by First Name");
        sortFirstNameButton.setOnAction(event -> {
            sortData(Comparator.comparing(User::getFirstName), firstNameSortAscending);
            firstNameSortAscending = !firstNameSortAscending;
        });

        Button sortLastNameButton = new Button("Sort by Last Name");
        sortLastNameButton.setOnAction(event -> {
            sortData(Comparator.comparing(User::getLastName), lastNameSortAscending);
            lastNameSortAscending = !lastNameSortAscending;
        });

        Button printIpGroupsButton = new Button("Print IP Groups");
        printIpGroupsButton.setOnAction(event -> printUsersGroupedByIpPrefix());

        VBox vbox = new VBox(firstNameFilter,
                lastNameFilter,
                emailFilter,
                imageLinkFilter,
                ipAddressFilter,
                recordCount,
                toUpperCaseButton,
                toLowerCaseButton,
                sortFirstNameButton,
                sortLastNameButton,
                printIpGroupsButton,
                table);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();

        loadData();
    }

    private void loadData() throws Exception {
        File file = new File("MOCK_DATA.csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        data.addAll(reader.lines().skip(1).map(line -> {
            String[] details = line.split(";");
            return new User(details[0], details[1], details[2], details[3], details[4]);
        }).collect(Collectors.toList()));

        reader.close();
        table.setItems(filteredData);
    }

    private void filterTableData() {
        String firstName = firstNameFilter.getText();
        String lastName = lastNameFilter.getText();
        String email = emailFilter.getText();
        String imageLink = imageLinkFilter.getText();
        String ipAddress = ipAddressFilter.getText();

        Predicate<User> predicate = user -> {
            if (firstName != null && !firstName.isEmpty() && !user.getFirstName().contains(firstName)) {
                return false;
            }
            if (lastName != null && !lastName.isEmpty() && !user.getLastName().contains(lastName)) {
                return false;
            }
            if (email != null && !email.isEmpty() && !user.getEmail().contains(email)) {
                return false;
            }
            if (imageLink != null && !imageLink.isEmpty() && !user.getImageLink().contains(imageLink)) {
                return false;
            }
            return !(ipAddress != null && !ipAddress.isEmpty() && !user.getIpAddress().contains(ipAddress));
        };

        List<User> filteredList = data.stream().filter(predicate).collect(Collectors.toList());
        filteredData = new FilteredList<>(FXCollections.observableArrayList(filteredList), p -> true);
        table.setItems(filteredData);
        recordCount.textProperty().bind(Bindings.size(filteredData).asString());
        filteredData.setPredicate(predicate);
    }


    private void changeCase(boolean toUpper) {
        List<User> updatedUsers = data.stream().map(user -> {
            String updatedFirstName = toUpper ? user.getFirstName().toUpperCase() : user.getFirstName().toLowerCase();
            String updatedLastName = toUpper ? user.getLastName().toUpperCase() : user.getLastName().toLowerCase();
            user.setFirstName(updatedFirstName);
            user.setLastName(updatedLastName);
            return user;
        }).collect(Collectors.toList());
        data = FXCollections.observableArrayList(updatedUsers);
        filteredData = new FilteredList<>(data, p -> true);
        table.setItems(filteredData);
        filterTableData();
        data.setAll(updatedUsers);
        filteredData.setPredicate(filteredData.getPredicate());
    }

    private void sortData(Comparator<User> comparator, boolean ascending) {
        Comparator<User> effectiveComparator = ascending ? comparator : comparator.reversed();
        List<User> sortedList = data.stream().sorted(effectiveComparator).collect(Collectors.toList());
        data = FXCollections.observableArrayList(sortedList);
        filteredData = new FilteredList<>(data, p -> true);
        table.setItems(filteredData);
        filterTableData();
    }

    public void printUsersGroupedByIpPrefix() {
        Map<String, List<User>> usersByIpPrefix = data.stream()
                .collect(Collectors.groupingBy(user -> user.getIpAddress().substring(0, 3)));

        usersByIpPrefix.forEach((ipPrefix, users) -> {
            System.out.println("IP Prefix: " + ipPrefix);
            users.forEach(user -> System.out.println("User: " + user.getFirstName() + " " + user.getLastName()));
        });
    }
}
