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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class TabReturnBookController implements Initializable {
    
    @FXML
    private TextField txtPersonReturnId;
    @FXML
    private Label lbReturnBkStatus;
    
    private KhachHang kh;
    private String lendNoteId;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kh = new KhachHang();
    }    
    
    @FXML
    protected void butConfirmReturnAction(ActionEvent event) {
        
        kh.setPhoneNumber(txtPersonReturnId.getText());
        // Make sure the input is a valid user
        if (!kh.usernameIsExists()) {
            Utils.showAlertWarn("Người dùng này không tồn tại");
        } else {
            kh.setNames();
            showMessage(getMessage(kh), lendNoteId);
        }
    }
    
    private String getMessage(KhachHang kh) {
        // Get the lend note info
        String[] lendNoteInfo =
                Database.getLendNoteInfo(
                        QueryHelper.getLendNoteNotReturned(kh.getPhoneNumber())
                );
        
        if (lendNoteInfo == null) {
            Utils.showAlertWarn("Khách hàng này không có phiếu mượn chưa trả.");
            return null;
        }
        
        lendNoteId = lendNoteInfo[0];
        
        // Get books of this lend note
        ArrayList<String> booksOfLendNote = 
                Database.getBooksOfLendNote(
                        QueryHelper.getBooksOfLendNote(lendNoteInfo[0]));
        
        if (booksOfLendNote.isEmpty()) {
            Utils.showAlertWarn("Có lỗi xảy ra khi lấy danh sách sách đã mượn.");
            return null;
        }
        
        StringBuilder message = new StringBuilder();
        message.append(String.format("Mã người mượn: %s", kh.getPhoneNumber()));
        message.append(String.format("\nTên người mượn: %s", kh.getFullName()));
        message.append("\nMã phiếu mượn: ");message.append(lendNoteInfo[0]);
        message.append(" - Ngày mượn: "); message.append(lendNoteInfo[1]);
        message.append("\n----------------------");
        for (String bk : booksOfLendNote) {
            message.append("\n");
            message.append(bk);
        }
        
        return message.toString();
    }
    
    private void showMessage(String mes, String lndNtId) {
        
        if (null == mes) {
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, mes);
        alert.setHeaderText("Xác nhận phiếu mượn");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int row = Database.insertData(QueryHelper.returnLendNote(lndNtId));
                if (row == 0) {
                    Utils.showAlertWarn("Có lỗi xảy ra khi trả sách.");
                } else {
                    lbReturnBkStatus.setText("Trả hoàn tất.");
                    Database.populateTable(QueryHelper.getAllLendNote(kh.getPhoneNumber()));
                }
            }
        });
    }
    
}
