/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.ComboBoxData;
import Data.Database;
import QLThuVien.Book;
import QLThuVien.QueryHelper;
import QLThuVien.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class TabAddBookController implements Initializable {

    @FXML
    private TextField txtBookTitle;
    @FXML
    private TextField txtAuthor;
    @FXML
    private ComboBox cbGenre;
    @FXML
    private ComboBox cbPublisher;
    @FXML
    private TextField txtYearPublished;
    @FXML
    private Label lbAddBookStatus;
    @FXML
    private Button butAddBook;
    @FXML
    private Label lbUpdateBookId;
    @FXML
    private Button butUpdateBook;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbGenre.getItems().addAll(ComboBoxData.populateCmbBoxTheLoai());
        cbPublisher.getItems().addAll(ComboBoxData.populateCmbBoxNxb());
    }

    @FXML
    protected void butAddBkAction(ActionEvent event) {

        if (txtBookTitle.getText().isEmpty()
                || txtAuthor.getText().isEmpty()
                || cbPublisher.getEditor().getText().isEmpty()
                || txtYearPublished.getText().isEmpty()) {
            Utils.showAlertOptional(
                    "Không được để trống bất kỳ trường nào.", "Thiếu thông tin", Alert.AlertType.INFORMATION);
        } else {
            Book bk = new Book();
            bk.setTitle(txtBookTitle.getText());
            bk.setAuthor(txtAuthor.getText());
            if (cbGenre.getSelectionModel().isEmpty()) {
                bk.setGenre("");
            } else {
                bk.setGenre(cbGenre.getSelectionModel().getSelectedItem().toString());
            }
            bk.setPublisher(cbPublisher.getEditor().getText());
            bk.setYearPublish(txtYearPublished.getText());
            bk.addBook(lbAddBookStatus);
            Database.populateTable(QueryHelper.selectAllBooks());
        }
    }

    @FXML
    protected void butUpdateBkAction(ActionEvent event) {

    }

    @FXML
    protected void butDelBkAaction(ActionEvent event) {

    }
}
