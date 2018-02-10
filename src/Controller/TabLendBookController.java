/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class TabLendBookController implements Initializable {

    //Lend book function tab
    @FXML
    private TextField txtBorrowerId;
    @FXML
    private ListView lstVBorrowBookList;
    @FXML
    private Label lbBorrwerId;
    @FXML
    private Label lbBorrowQuantity;
    @FXML
    private Label lbReturnDay;
    @FXML
    private Label lbLendBookStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    protected void butCheckBorrowerAction(ActionEvent event) {

    }
    @FXML
    protected void butAddBorrowBookAction(ActionEvent event) {

    }
    @FXML
    protected void butDelBorrowBook(ActionEvent event) {

    }
    
    @FXML
    protected void butConfirmBorrowBook(ActionEvent event) {

    }
    @FXML
    protected void butLendBookAction(ActionEvent event) {

    }

}
