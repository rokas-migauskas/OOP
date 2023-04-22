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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import loan.AnnuityLoan;
import loan.Loan;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML
    private TableColumn<TableData, Integer> index;

    @FXML
    private TableColumn<TableData, Float> mortgage;

    @FXML
    private TableColumn<TableData, Float> monthlySum;

    @FXML
    private TableColumn<TableData, Float> repayment;

    @FXML
    private TableColumn<TableData, Float> interest;

    @FXML
    private TableView<TableData> table;

    @FXML
    private Button outputToFileButton;

    @FXML
    private Button lineChartButton;

    public TextField filenameField;
    private Loan loan;

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Loan getLoan() {
        return loan;
    }

    public ObservableList<TableData> getList() {
        return list;
    }


    public static ObservableList <TableData> list = FXCollections.observableArrayList();



    public void setList(ObservableList<TableData> list) {
        this.list = list;
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        ObservableList <TableData> list = FXCollections.observableArrayList();
        setLoan(MainController.loan);
        index.setText("Month");
        if(loan.loanType == "Line") {
            repayment.setText("Total Payment");
        }

        for(int i = 0; i < loan.duration + (MainController.deferalEndMonth-MainController.deferalStartMonth); ++i){
            if(i < MainController.deferalStartMonth || i > MainController.deferalEndMonth-1) {
                try {
                    loan.getMortgage(i);
                } catch(Exception e){
                    System.out.println(i);
                }

                list.add(new TableData(i+1, loan.getMonthlyPayment(), loan.getCurrentInterest(), loan.getCurrentRepayment(), loan.getCurrentMortgage()));
            } else {
                loan.increaseInterest(MainController.deferalInterestRate);
                list.add(new TableData(i+1, loan.getMonthlyPayment(), loan.getCurrentInterest(), loan.getCurrentRepayment(), loan.getCurrentMortgage()));
            }

        }
        index.setCellValueFactory(new PropertyValueFactory<TableData, Integer>("index"));
        monthlySum.setCellValueFactory(new PropertyValueFactory<TableData, Float>("monthlySum"));
        interest.setCellValueFactory(new PropertyValueFactory<TableData, Float>("interest"));
        repayment.setCellValueFactory(new PropertyValueFactory<TableData, Float>("repayment"));
        mortgage.setCellValueFactory(new PropertyValueFactory<TableData, Float>("mortgage"));


        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        Callback<TableColumn<TableData, Float>, TableCell<TableData, Float>> floatCellFactory = column -> {
            return new TableCell<TableData, Float>() {
                @Override
                protected void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(decimalFormat.format(item));
                    }
                }
            };
        };
        monthlySum.setCellFactory(floatCellFactory);
        interest.setCellFactory(floatCellFactory);
        repayment.setCellFactory(floatCellFactory);
        mortgage.setCellFactory(floatCellFactory);

        trimObservableArrayList(list);
        table.setItems(list);
        setList(list);

    }
    public void outputToFile() throws IOException {
        String filename = filenameField.getText();
        writeObservableListToCsv(list, filename+".csv");
    }
    public void trimObservableArrayList (ObservableList list){
        if (MainController.endMonth !=0) {
            for(int i = MainController.endMonth; i < loan.duration; ++i) {
                list.remove(MainController.endMonth);
            }
            for(int i = 0; i < MainController.startMonth-1; ++i) {
                list.remove(0);
            }

        }

    }


    public static void writeObservableListToCsv(List<TableData> observableList, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Index", "Monthly Sum", "Interest", "Repayment", "Mortgage"));

        for (TableData data : observableList) {
            csvPrinter.printRecord(data.getIndex(), data.getMonthlySum(), data.getInterest(), data.getRepayment(), data.getMortgage());
        }

        csvPrinter.flush();
        csvPrinter.close();
        writer.close();
    }
    public void goToMenu(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("Loan Calculator");
        window.setScene(scene);
        window.show();
    }
    public void goToLineChart(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("linechart.fxml"));
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setTitle("Line Chart");
        window.setScene(scene);
        window.show();
    }
}
