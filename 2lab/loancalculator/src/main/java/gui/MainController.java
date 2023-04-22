package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import loan.AnnuityLoan;
import javafx.scene.control.TextField;
import loan.LineLoan;
import loan.Loan;

import java.io.IOException;

public class MainController {

    public TextField amountField;
    public TextField timeframeYearsField;
    public TextField yearlyPercentageField;
    public TextField timeframeMonthsField;
    public TextField startMonthField;
    public TextField endMonthField;

    public TextField deferalStartField;
    public TextField deferalInterestRateField;

    public TextField deferalEndField;
    public RadioButton annuityChoice;
    public RadioButton lineChoice;
    public ToggleGroup choice;

    public static Loan loan;

    public static int startMonth;
    public static int endMonth;

    public static int deferalStartMonth;
    public static int deferalEndMonth;
    public static int deferalInterestRate;

    @FXML
    private Button btnNewWindow;
    @FXML

    TableController tableController = new TableController();

    public void handleBtnNewWindow(ActionEvent actionEvent) throws IOException {
        float amount = 0;
        if (!amountField.getText().isEmpty()) {
            amount = Float.parseFloat(amountField.getText());
        }
        int years = 0;
        if (!timeframeYearsField.getText().isEmpty()) {
            years = Integer.parseInt(timeframeYearsField.getText());
        }
        int months = 0;
        if (!timeframeMonthsField.getText().isEmpty()) {
            months = Integer.parseInt(timeframeMonthsField.getText());
        }
        float yearlyPercentage = 0;
        if (!yearlyPercentageField.getText().isEmpty()) {
            yearlyPercentage = Float.parseFloat(yearlyPercentageField.getText())/100;
        }
        startMonth = 0;
        if (!startMonthField.getText().isEmpty()) {
            startMonth = Integer.parseInt(startMonthField.getText());
        }
        deferalEndMonth = 0;
        if (!deferalEndField.getText().isEmpty()) {
            deferalEndMonth = Integer.parseInt(deferalEndField.getText());
        }
        deferalStartMonth = 0;
        if (!deferalStartField.getText().isEmpty()) {
            deferalStartMonth = Integer.parseInt(deferalStartField.getText());
        }
        deferalInterestRate = 0;
        if (!deferalInterestRateField.getText().isEmpty()) {
            deferalInterestRate = Integer.parseInt(deferalInterestRateField.getText());
        }
        endMonth = 0;
        if (!endMonthField.getText().isEmpty()) {
            endMonth = Integer.parseInt(endMonthField.getText());
        }
        String loanType;
        if(choice.getSelectedToggle().equals(annuityChoice)) {
            loanType = "Annuity";
            loan = new AnnuityLoan(amount, years, months, yearlyPercentage, loanType);

        } else {
            loanType = "Line";
            loan = new LineLoan(amount, years, months, yearlyPercentage, loanType);

        }

        Parent parent = FXMLLoader.load(getClass().getResource("table.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("table.fxml"));
        //fxmlLoader.setController(tableController);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("Table");
        window.setScene(scene);
        window.show();
    }



}