/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXML;

import QLThuVien.Admin;
import Data.Database;
import QLThuVien.KhachHang;
import QLThuVien.Utils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Doctor
 */
public class LogInController {

    @FXML
    public RadioButton radLogAsAdmin;
    @FXML
    public RadioButton radLogAsGuess;
    @FXML
    private Label lbStatus;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField passUserPass;

    private String username;
    private String pass;

    private KhachHang khachHang;
    private Admin admin;

    @FXML
    public void initialize() {
        lbStatus.setText(Database.testConnection());
    }

    public void butLogInClicked(ActionEvent actionEvent) {

        //Set username and pass for futher using
        username = txtUsername.getText().trim();
        pass = passUserPass.getText().trim();

        //Perform validate information
        if (username.isEmpty() || pass.isEmpty()) { //Check if any fields is empty
            lbStatus.setText("Bạn chưa nhập tên đăng nhập hoặc mật khẩu!");
        } else if (radLogAsAdmin.isSelected()) { //Check what role to log in
            admin = new Admin(username, pass);
            if (admin.isValid()) { //Check info, if valid => log in
                Utils.setCurrentUser(admin);
                showManageForm(actionEvent);
            } else {
                lbStatus.setText("Sai tên đăng nhập hoặc mật khẩu!");
            }
        } else { //Log in as guest
            khachHang = new KhachHang(username, pass);
            if (khachHang.isValid()) { //Check info
                Utils.setCurrentUser(khachHang);
                showManageForm(actionEvent);
            } else {
                lbStatus.setText("Sai tên đăng nhập hoặc mật khẩu!");
            }
        }
    }

    private void showManageForm(ActionEvent actionEvent) {
        try {
            lbStatus.setText("Log in button Clicked!");

            Parent root;
            root = FXMLLoader.load(getClass().getResource("/FXML/QuanLyChung.fxml"));
            Stage libManagerStage;
            libManagerStage = new Stage();
            libManagerStage.setTitle("Quản lý thư viện");
            libManagerStage.getIcons().add(new Image("/Image/ic_library.png"));
            libManagerStage.setScene(new Scene(root));
            libManagerStage.setMinWidth(800);
            libManagerStage.setMinHeight(600);
            libManagerStage.setOnCloseRequest((WindowEvent event) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn muốn thoát?");
                alert.showAndWait().ifPresent(response -> {
                    if (response != ButtonType.OK) {
                        event.consume();
                    }
                });
            });
            libManagerStage.show();

            //Hide this window because don't need it anymore
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();

        } catch (NullPointerException nullException) {
            lbStatus.setText("Log in failed because: "
                    + nullException.getMessage()
                    + "\nMaybe the neccessary file .fxml is not found.");
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void butExitClicked(ActionEvent actionEvent) {
        Utils.confirmExit();
    }

}
