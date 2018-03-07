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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class TabTraThongTinDocGiaController implements Initializable {

    @FXML
    private TextField txtPhoneNum;
    @FXML
    private VBox vBoxListBooksOfLendNote;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    protected void butGetUserInfoAction(ActionEvent event) {

        if (txtPhoneNum.getText().isEmpty()) {
            showMessage(
                    Utils.getCurrentUserId()
                    , Utils.getCurrentUserFullName()
                    , Utils.getCurrentUserRole());
        } else {

            String id = txtPhoneNum.getText();
            String role;
            String[] result ;
            if (Utils.currentUserIsAdmin()) {
                result = Database.getUserName(QueryHelper.searchUserInAdmin(id));
                role = "Admin";
                if (result == null) {
                    result = Database.getUserName(QueryHelper.searchUserInKhachHang(id));
                    role = "Khách hàng";
                }
            } else {
                result = Database.getUserName(QueryHelper.searchUserInKhachHang(id));
                role = "Khách hàng";
            }

            if (result == null) {
                Utils.showAlertOptional("Không tìm thấy.", "Kết quả", Alert.AlertType.INFORMATION);
            } else {
                String name = result[0] + " " + result[1];
                showMessage(id, name, role);
            }

        }
    }

    @FXML
    protected void butGetOldLendNoteAction(ActionEvent event) {

    }

    private void showMessage(String id, String name, String role) {
        
        String headTitle = "Thông tin cho " + name;
        
        StringBuilder mes = new StringBuilder();
        mes.append("Mã người dùng: ");
        mes.append(id);
        mes.append("\n");
        mes.append("Tên người dùng: ");
        mes.append(name);
        mes.append("\n");
        mes.append("Vai trò: ");
        mes.append(role);

        Utils.showAlertOptional(mes.toString(), headTitle, Alert.AlertType.INFORMATION);
    }
}
