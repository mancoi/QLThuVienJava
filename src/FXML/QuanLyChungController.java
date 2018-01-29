/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXML;

import QLThuVien.Admin;
import QLThuVien.KhachHang;
import QLThuVien.LogIn;
import QLThuVien.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class QuanLyChungController implements Initializable{
    
    @FXML TabPane tabPaneQuanLy;
    @FXML Tab tabThongTinDocGia;
    @FXML MenuButton menuButtonUsername;
    
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //tabPaneQuanLy.getTabs().remove(tabThongTinDocGia);
        menuButtonUsername.setText("Xin chào " + Utils.getCurrentUserFullName());
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
