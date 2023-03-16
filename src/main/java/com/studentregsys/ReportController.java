package com.studentregsys;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ReportController {

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    private DataManager dataManager;
    private GroupManager groupManager;
    @FXML
    private Button generateReportButton;

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void initGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }
    @FXML
    private void generateReport() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        PDFReport pdfReport = new PDFReport();
        pdfReport.initGroupManager(groupManager);
        pdfReport.setDataManager(dataManager);
        String fileName = "student_report_" + startDate + "_to_" + endDate + ".pdf";
        try {
            pdfReport.generateReport(dataManager, startDate, endDate, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Stage stage = (Stage) generateReportButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent menuRoot = loader.load();
            Scene menuScene = new Scene(menuRoot);
            MainController mainController = loader.getController();
            mainController.setDataManager(dataManager);
            mainController.initGroupManager(groupManager);
            stage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
