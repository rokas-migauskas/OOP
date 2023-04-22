package gui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import loan.Loan;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LineChartController implements Initializable {

    private Loan loan;
    private ObservableList<TableData> list = FXCollections.observableArrayList();

    @FXML
    private LineChart lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public void setList(ObservableList<TableData> list) {
        this.list = list;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setList(TableController.list);
        setLoan(MainController.loan);
        xAxis.setLabel("Month");
        yAxis.setLabel("Money");
        XYChart.Series monthlySum = new XYChart.Series();
        XYChart.Series interest = new XYChart.Series();
        XYChart.Series repayment = new XYChart.Series();
        if(MainController.loan.loanType == "Annuity") {
            monthlySum.setName("Annuity");
            interest.setName("Interest");
            repayment.setName("Repayment");
        } else {
            monthlySum.setName("Redeeming");
            interest.setName("Interest");
            repayment.setName("Total Payment");
        }


        for (int i = MainController.startMonth; i < list.size()+MainController.startMonth; i++) {
            monthlySum.getData().add(new XYChart.Data(String.valueOf(i+1), list.get(i).getMonthlySum()));
        }
        lineChart.getData().add(monthlySum);

        for (int i = 0; i < list.size(); i++) {
            interest.getData().add(new XYChart.Data(String.valueOf(i+1), list.get(i).getInterest()));
        }
        lineChart.getData().add(interest);


        for (int i = 0; i < list.size(); i++) {
            repayment.getData().add(new XYChart.Data(String.valueOf(i+1), list.get(i).getRepayment()));
        }
        lineChart.getData().add(repayment);


    }

    public void goToMenu(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("Loan Calculator");
        window.setScene(scene);
        window.show();
    }
}
