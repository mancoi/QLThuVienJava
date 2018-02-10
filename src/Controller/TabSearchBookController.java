/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.ComboBoxData;
import QLThuVien.Book;
import QLThuVien.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class TabSearchBookController implements Initializable {

    //SearchBook function tab
    @FXML
    private TextField txtBookIDToSearch;
    @FXML
    private TextField txtBookTitleToSearch;
    @FXML
    private ComboBox cmbBoxBookAuthorToSearch;
    @FXML
    private ComboBox cmbBoxBookGenreToSearch;
    @FXML
    private ComboBox cmbBoxBookPublishYearToSearch;
    @FXML
    private Label lbSearchStatus;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Populate ComboBox
        cmbBoxBookAuthorToSearch.getItems().addAll(ComboBoxData.populateCmbBoxTacGia());
        cmbBoxBookGenreToSearch.getItems().addAll(ComboBoxData.populateCmbBoxTheLoai());
        cmbBoxBookPublishYearToSearch.getItems().addAll(ComboBoxData.populateCmbBoxNamXuatBan());
        
        //When user find with BookId, they shouldn't add more info
        txtBookIDToSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.trim().isEmpty()) {
                    Utils.enableControls(txtBookTitleToSearch, cmbBoxBookAuthorToSearch, cmbBoxBookPublishYearToSearch, cmbBoxBookGenreToSearch);
                } else {
                    Utils.disableControls(txtBookTitleToSearch, cmbBoxBookAuthorToSearch, cmbBoxBookPublishYearToSearch, cmbBoxBookGenreToSearch);
                }
            }
        });
    }    
    
      //Perform search book
    @FXML
    protected void butSearchBookAction(ActionEvent event) {

        lbSearchStatus.setText(null);
        int rows;
        Book book = new Book();
        try {

            //If user not enter a bookId, assign it to -1
            String bId = txtBookIDToSearch.getText().trim();
            book.setBookId(bId.isEmpty() ? -1 : Integer.parseInt(bId));
            //Get data from another field
            book.setTitle(txtBookTitleToSearch.getText().trim());
            book.setAuthor(cmbBoxBookAuthorToSearch.getEditor().getText().trim());
            book.setGenre(cmbBoxBookGenreToSearch.getEditor().getText().trim());
            book.setYearPublish(cmbBoxBookPublishYearToSearch.getEditor().getText().trim());
            //Perform search
            rows = book.searchBook();

            if (0 == rows) {
                lbSearchStatus.setText("Không có kết quả");
            }

        } catch (NumberFormatException ex) {
            lbSearchStatus.setText("Mã sách không hợp lệ");
            System.err.println("FXML.QuanLyChungController.butSearchBookAction() "
                    + ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.print("QuanLyChungController: " + ex.getMessage());
        }
    }
    
    @FXML
    protected void clearAllSearchField(ActionEvent event) {
        txtBookIDToSearch.setText("");
        txtBookTitleToSearch.setText("");
        cmbBoxBookAuthorToSearch.setValue(null);
        cmbBoxBookGenreToSearch.setValue(null);
        cmbBoxBookPublishYearToSearch.setValue(null);
        lbSearchStatus.setText(null);
    }
    
}
