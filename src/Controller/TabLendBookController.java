/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Database;
import QLThuVien.KhachHang;
import QLThuVien.QueryHelper;
import QLThuVien.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Label lbCheckBrwStatus;
    @FXML
    private Label lbLendBookStatus;
    @FXML
    private Button butCheckBorrower;
    @FXML
    private Button butAddBorrowBook;
    @FXML
    private Button butDelBorrowBook;
    @FXML
    private Button butConfirmBorrowBook;

    private KhachHang kh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kh = new KhachHang();
    }

    @FXML
    protected void butCheckBorrowerAction(ActionEvent event) {
        kh.setPhoneNumber(txtBorrowerId.getText().trim());
        String query = QueryHelper.checkBorrower(kh.getPhoneNumber());

        if (!kh.usernameIsExists()) {
            Utils.showAlert("Người dùng này không tồn tại");
        } else if (!Database.isReturnedAllBook(query)) {
            Database.populateTable(query);
            Utils.showAlert("Không thể cho mượn!\nNgười dùng này vẫn còn phiếu mượn chưa trả");
        } else {
            lbCheckBrwStatus.setText("OK");
            Database.populateTable(QueryHelper.selectAllBooks());
            Utils.enableControls(
                      lstVBorrowBookList
                    , butAddBorrowBook
                    , butDelBorrowBook
                    , butConfirmBorrowBook);
            butCheckBorrower.setDisable(true);
        }
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
