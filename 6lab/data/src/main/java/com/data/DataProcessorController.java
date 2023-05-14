package com.data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataProcessorController {

    private static final String SUBDIRECTORY = "files";

    private ExecutorService threadPool = Executors.newFixedThreadPool(10); // 10 threads
    @FXML
    private TableView<Person> dataTable;

    @FXML
    private TableView<Person> completedTasksTable;

    @FXML
    private Button deleteAllButton;

    private ObservableList<Person> data;
    private ObservableList<Person> completedTasks;

    @FXML
    public void initialize() {
        data = readDataFromFile("MOCK_DATA.csv");
        dataTable.setItems(data);
        initializeDataTableColumns();

        completedTasks = FXCollections.observableArrayList();
        completedTasksTable.setItems(completedTasks);
        initializeCompletedTasksTableColumns();

        deleteAllButton.setOnAction(event -> {
            for (Person person : completedTasks) {
                deletePersonFile(person);
            }
            data.addAll(completedTasks);
            completedTasks.clear();
        });

        processAllRows();
    }

    private ObservableList<Person> readDataFromFile(String filePath) {
        ObservableList<Person> data = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                data.add(new Person(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    private void initializeDataTableColumns() {
        TableColumn<Person, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Person, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Person, String> imageLinkColumn = new TableColumn<>("Image Link");
        imageLinkColumn.setCellValueFactory(new PropertyValueFactory<>("imageLink"));
        TableColumn<Person, String> ipAddressColumn = new TableColumn<>("IP Address");
        ipAddressColumn.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));

        dataTable.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, emailColumn, imageLinkColumn, ipAddressColumn);
    }

    private void initializeCompletedTasksTableColumns() {
        TableColumn<Person, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Person, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Person, String> imageLinkColumn = new TableColumn<>("Image Link");
        imageLinkColumn.setCellValueFactory(new PropertyValueFactory<>("imageLink"));
        TableColumn<Person, String> ipAddressColumn = new TableColumn<>("IP Address");
        ipAddressColumn.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));

        TableColumn<Person, Button> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    Person person = getTableView().getItems().get(getIndex());
                    deleteButton.setOnAction(event -> {
                        deletePersonFile(person);
                        completedTasks.remove(person);
                        data.add(person);
                    });
                }
            }
        });

        completedTasksTable.getColumns().addAll(idColumn, firstNameColumn, deleteColumn);
    }

    private void processAllRows() {
        for (Person person : data) {
            threadPool.submit(() -> processRow(person));
        }
        threadPool.shutdown(); // Important to shutdown the executor when done
    }

    private void processRow(Person person) {
        savePersonToFile(person);

        try {
            Thread.sleep(100); // Pause for to simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Platform.runLater(() -> {
            data.remove(person);
            completedTasks.add(person);
        });
    }


    private void savePersonToFile(Person person) {
        String firstNamePart = person.getFirstName().length() >= 3 ? person.getFirstName().substring(0, 3) : person.getFirstName();
        String lastNamePart = person.getLastName().length() >= 3 ? person.getLastName().substring(0, 3) : person.getLastName();
        String ipAddressPart = person.getIpAddress().substring(person.getIpAddress().lastIndexOf('.') + 1);

        String fileName = firstNamePart + lastNamePart + ipAddressPart + ".txt";

        // Ensure the subdirectory exists
        File directory = new File(SUBDIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SUBDIRECTORY + File.separator + fileName))) {
            bw.write(person.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deletePersonFile(Person person) {
        String firstNamePart = person.getFirstName().length() >= 3 ? person.getFirstName().substring(0, 3) : person.getFirstName();
        String lastNamePart = person.getLastName().length() >= 3 ? person.getLastName().substring(0, 3) : person.getLastName();
        String ipAddressPart = person.getIpAddress().substring(person.getIpAddress().lastIndexOf('.') + 1);

        String fileName = firstNamePart + lastNamePart + ipAddressPart + ".txt";
        File file = new File(SUBDIRECTORY + File.separator + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}

