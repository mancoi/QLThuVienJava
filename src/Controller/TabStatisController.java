/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Database;
import QLThuVien.QueryHelper;
import QLThuVien.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class TabStatisController implements Initializable {

    @FXML
    private Label lbStReaderCount;
    @FXML
    private Label lbStAdminCount;
    @FXML
    private Label lbStLndNoteCount;
    @FXML
    private PieChart pcBookByYear;
    @FXML
    private Label lbStBookCount;
    @FXML
    private Label lbStPublisherCount;
    @FXML
    private Label lbStGenreCount;

    private ArrayList<String> lstYears;
    private ArrayList<Integer> numOfBook;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Count
        String cReader = Database.getCountResultAsString(QueryHelper.countReader());
        String cAdmin = Database.getCountResultAsString(QueryHelper.countAdmin());
        String cLNote = Database.getCountResultAsString(QueryHelper.countLendNote());
        String cBook = Database.getCountResultAsString(QueryHelper.countBook());
        String cPublisher = Database.getCountResultAsString(QueryHelper.countPublisher());
        String cGenre = Database.getCountResultAsString(QueryHelper.countGenre());

        lbStReaderCount.setText(cReader);
        lbStAdminCount.setText(cAdmin);
        lbStLndNoteCount.setText(cLNote);
        lbStBookCount.setText(cBook);
        lbStPublisherCount.setText(cPublisher);
        lbStGenreCount.setText(cGenre);

        pcBookByYear.setLegendVisible(false);
        pcBookByYear.setTitle("Số lượng sách theo các năm");

        //Set up PieChart
        lstYears
                = Database.getAsArrayListOfString(QueryHelper.getListOfYears());
        numOfBook = new ArrayList<>();
        for (int i = 0; i < lstYears.size(); i++) {
            numOfBook.add(
                    Database.getCountResultAsInt(
                            QueryHelper.getBooksOfSpecificYear(lstYears.get(i))));
        }

        for (int i = 0; i < lstYears.size(); i++) {
            int nOb = numOfBook.get(i);
            pcBookByYear.getData()
                    .add(new PieChart.Data(lstYears.get(i), nOb));
        }
    }

    @FXML
    private void butSelectPublsherAction(ActionEvent event) {
        Database.populateTable(QueryHelper.selectAllPublisher());
    }

    @FXML
    private void butSelectGenreAction(ActionEvent event) {
        Database.populateTable(QueryHelper.selectAllGenre());
    }

    @FXML
    private void butStRefreshAction(ActionEvent event) {
        pcBookByYear.getData().clear();
        initialize(null, null);
    }

    @FXML
    private void butSelectReaderAction(ActionEvent event) {
        Database.populateTable(QueryHelper.selectAllReader());
    }

    @FXML
    private void butBookOfYearsDetailAction(ActionEvent event) {
        StringBuilder mes = new StringBuilder();
        for (int i = 0; i < lstYears.size(); i++) {
            mes.append("Năm ");
            mes.append(lstYears.get(i));
            mes.append(": ");
            mes.append(numOfBook.get(i));
            mes.append(" cuốn");
            mes.append("\n");
        }

        Utils.showAlertOptional(
                mes.toString(), "Chi tiết số lượng sách của các năm", Alert.AlertType.INFORMATION);
    }

}
