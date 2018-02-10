/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.ComboBoxData;
import Data.Database;
import QLThuVien.Admin;
import QLThuVien.Book;
import QLThuVien.KhachHang;
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

        //Set the tableView
        Utils.setTblViewResult(tblViewResult);

        //tabPaneQuanLy.getTabs().remove(tabThongTinDocGia);
        menuButtonUsername.setText("Xin chào " + Utils.getCurrentUserFullName());
        Database.populateTable(QueryHelper.selectAllBooks());

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

    //TableView
    @FXML
    private TableView tblViewResult;
}
