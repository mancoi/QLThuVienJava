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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableView;
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
    @FXML
    private Button butDelBook;
    @FXML
    private Button butConfirmUse;

    private boolean listenerAdded = false;
    private String selectedBookId = "";

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
    protected void butUseUpdateDeleteFuncAction(ActionEvent event) {
        if (!listenerAdded) {
            addListener();
            listenerAdded = true;
        }
        butConfirmUse.setDisable(true);
        butUpdateBook.setDisable(false);
        butDelBook.setDisable(false);
    }

    @FXML
    protected void butUpdateBkAction(ActionEvent event) {
        if (selectedBookId.isEmpty()) {
            Utils.showAlertOptional(
                    "Hãy chọn một quyển sách để cập nhật thông tin.", "Chưa chọn quyển sách nào.", Alert.AlertType.INFORMATION);
        } else {
            Book bk = new Book();
            bk.setBookId(Integer.parseInt(selectedBookId));
            bk.setTitle(txtBookTitle.getText());
            bk.setAuthor(txtAuthor.getText());

            bk.setGenre(cbGenre.getSelectionModel().getSelectedItem().toString());
            bk.setGenreId(bk.getGenreId(bk.getGenre()));

            bk.setPublisher(cbPublisher.getEditor().getText());
            bk.setPublisherId(bk.getPublisherId(bk.getPublisher()));

            bk.setYearPublish(txtYearPublished.getText());

            bk.updateBook();
            Database.populateTable(QueryHelper.selectAllBooks());
        }
    }

    @FXML
    protected void butDelBkAaction(ActionEvent event) {
        if (selectedBookId.isEmpty()) {
            Utils.showAlertOptional(
                    "Hãy chọn một quyển sách để xóa.", "Chưa chọn quyển sách nào.", Alert.AlertType.INFORMATION);
        } else {
            Alert alert = new Alert(
                    Alert.AlertType.WARNING, "Bạn có thật sự muốn xóa?\nSách đã xóa không thể khôi phục được.");
            alert.setHeaderText("Cảnh báo");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Book bk = new Book();
                    bk.setBookId(Integer.parseInt(selectedBookId));
                    bk.deleteBook();

                    Database.populateTable(QueryHelper.selectAllBooks());
                }
            });

        }
    }

    @FXML
    protected void butClearFieldsAction(ActionEvent event) {
        clearFields();
    }
    
    private void clearFields() {
        txtBookTitle.clear();
        txtAuthor.clear();
        cbGenre.getSelectionModel().clearSelection();
        cbPublisher.getSelectionModel().clearSelection();
        txtYearPublished.clear();
    }

    private void addListener() {
        TableView tbvBooks = Utils.getTblViewResult();
        // Add the listener for the TableView to 
        // get books in the selected lend note.
        tbvBooks.getSelectionModel().selectedItemProperty().addListener((Observable e) -> {

            // If the user still not selected any item, do nothing.
            if (!tbvBooks.getSelectionModel().isEmpty()) {

                try {
                    String selectedBooks
                            = tbvBooks.getSelectionModel().getSelectedItem().toString();
                    System.out.println(selectedBooks);
                    // Add each field to String arry fileds
                    List<String> fields = new ArrayList<>(Arrays.asList(
                            selectedBooks
                            .replaceAll("\\[|\\]", "")
                            .replaceAll(", ", ",")
                            .split(",")));

                    if (fields.size() == 7) {
                        fields.set(2, fields.get(2).concat(", " + fields.get(3)));
                        fields.remove(3);
                    }

                    txtYearPublished.setText(fields.get(5));
                    
                    selectedBookId = fields.get(0);
                    lbUpdateBookId.setText(fields.get(1));

                    txtBookTitle.setText(fields.get(1));
                    txtAuthor.setText(fields.get(2));

                    cbGenre.setValue(fields.get(3));
                    cbPublisher.setValue(fields.get(4));
                    
                } catch (IndexOutOfBoundsException err) { 
                    lbUpdateBookId.setText("");
                    clearFields();
                }

            }

        });
    }
}
