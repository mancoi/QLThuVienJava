/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Database;
import QLThuVien.QueryHelper;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
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
        
        //Set up PieChart
        ArrayList<String> lstYears = 
                Database.getAsArrayListOfString(QueryHelper.getListOfYears());
        ArrayList<Integer> numOfBook = new ArrayList<>();
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
    }

    @FXML
    private void butSelectGenreAction(ActionEvent event) {
    }

    @FXML
    private void butStRefreshAction(ActionEvent event) {
        initialize(null, null);
    }

    @FXML
    private void butSelectReaderAction(ActionEvent event) {
    }
    
}
