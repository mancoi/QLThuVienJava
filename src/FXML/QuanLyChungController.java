/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXML;

import QLThuVien.Admin;
import QLThuVien.Database;
import QLThuVien.KhachHang;
import QLThuVien.LogIn;
import QLThuVien.QueryHelper;
import QLThuVien.Utils;
import com.sun.javafx.property.adapter.PropertyDescriptor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class QuanLyChungController implements Initializable {

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
    private ComboBox cmbBoxBookPublisherToSearch;
    @FXML
    private ComboBox cmbBoxBookPublishYearToSearch;
    @FXML
    private Label lbSearchStatus;
    //TableView
    @FXML
    private TableView tblViewResult;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //tabPaneQuanLy.getTabs().remove(tabThongTinDocGia);
        menuButtonUsername.setText("Xin chào " + Utils.getCurrentUserFullName());
        Database.populateTable(QueryHelper.selectAllBooks(), tblViewResult);
       
        txtBookIDToSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    Utils.enableContorls(txtBookTitleToSearch
                            , cmbBoxBookAuthorToSearch
                            , cmbBoxBookPublishYearToSearch
                            , cmbBoxBookPublisherToSearch);
                } else {
                    Utils.disableContorls(txtBookTitleToSearch
                            , cmbBoxBookAuthorToSearch
                            , cmbBoxBookPublishYearToSearch
                            , cmbBoxBookPublisherToSearch);
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
        int id = -1;
        try {
            id = Integer.parseInt(txtBookIDToSearch.getText().trim());
        } catch (NumberFormatException ex) {
            System.err.println("FXML.QuanLyChungController.butSearchBookAction() "
                    + ex.getMessage());
        }
        String bookTitle = txtBookTitleToSearch.getText().trim();
        //String bookAuthor = cmbBoxBookAuthorToSearch.getText().trim();
        //String publisher = txtBookPublisherToSearch.getText().trim();
        //String year = txtBookPublishYearToSearch.getText().trim();

        if (-1 != id) {
            Database.populateTable(QueryHelper.searchBookById(id), tblViewResult);
        } else if (true) {
            
        }
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
}
