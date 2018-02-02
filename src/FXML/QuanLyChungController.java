/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXML;

import Data.ComboBoxData;
import Data.Database;
import QLThuVien.QueryHelper;
import QLThuVien.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class QuanLyChungController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //tabPaneQuanLy.getTabs().remove(tabThongTinDocGia);
        menuButtonUsername.setText("Xin chào " + Utils.getCurrentUserFullName());
        Database.populateTable(QueryHelper.selectAllBooks(), tblViewResult);

        //Populate ComboBox
        cmbBoxBookAuthorToSearch.getItems().addAll(ComboBoxData.populateCmbBoxTacGia());
        cmbBoxBookGenreToSearch.getItems().addAll(ComboBoxData.populateCmbBoxTheLoai());
        cmbBoxBookPublishYearToSearch.getItems().addAll(ComboBoxData.populateCmbBoxNamXuatBan());

        //When user find with BookId, they shouldn't add more info
        txtBookIDToSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.trim().isEmpty()) {
                    Utils.enableContorls(txtBookTitleToSearch, cmbBoxBookAuthorToSearch, cmbBoxBookPublishYearToSearch, cmbBoxBookGenreToSearch);
                } else {
                    Utils.disableContorls(txtBookTitleToSearch, cmbBoxBookAuthorToSearch, cmbBoxBookPublishYearToSearch, cmbBoxBookGenreToSearch);
                }
            }
        });
    }

    @FXML
    protected void logOutAction(ActionEvent event) {
        try {
            Utils.reset();
            showPrimaryStage();
            ((Node) menuButtonUsername).getScene().getWindow().hide();

        } catch (Exception ex) {
            System.err.println("QLChungController: " + ex.getMessage());
        }
    }

    @FXML
    protected void butSearchBookAction(ActionEvent event) {

        lbSearchStatus.setText(null);
        int rows;
        try {

            //If user not enter a bookId, assign it to -1
            String bId = txtBookIDToSearch.getText().trim();
            int id = bId.isEmpty() ? -1 : Integer.parseInt(bId);
            //Get data from another field
            String bookTitle = txtBookTitleToSearch.getText().trim();
            String bookAuthor = cmbBoxBookAuthorToSearch.getEditor().getText().trim();
            String genre = cmbBoxBookGenreToSearch.getEditor().getText().trim();
            String year = cmbBoxBookPublishYearToSearch.getEditor().getText().trim();

            if (-1 != id) {
                rows = Database.populateTable(QueryHelper.searchBookById(id), tblViewResult);
            } else {
                String[] citeria = {bookTitle, bookAuthor, genre, year};
                rows = Database.populateTable(QueryHelper.searchBook(citeria), tblViewResult);
            }
            
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

    @FXML
    protected void exitMenuItemAction(ActionEvent event) {
        Utils.confirmExit();
    }

    private void showPrimaryStage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/LogInFXML.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Quản lý thư viện");
        primaryStage.getIcons().add(new Image("/Image/ic_library.png"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn muốn thoát?");
            alert.showAndWait().ifPresent(response -> {
                if (response != ButtonType.OK) {
                    event.consume();
                }
            });
        });

        primaryStage.show();
    }

    //Menu
    @FXML
    private TabPane tabPaneQuanLy;
    @FXML
    private Tab tabThongTinDocGia;
    @FXML
    private MenuButton menuButtonUsername;
    //SearchBook tab
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
    //TableView
    @FXML
    private TableView tblViewResult;
}
