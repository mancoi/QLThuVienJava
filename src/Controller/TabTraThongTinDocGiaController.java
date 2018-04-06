/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Database;
import QLThuVien.QueryHelper;
import QLThuVien.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
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
    private ListView listViewBooksOfLendNote;
    @FXML
    private VBox vBoxBksOfLNoteContainer;
    @FXML
    private Label lbLendNoteId;
    @FXML
    private Button butGetOldLendNote;

    // Make sure only set listener for the TableView one time.
    private boolean listenerAdded = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Disable the get user lend note if nothing has entered the text field
        txtPhoneNum.textProperty().addListener(e -> {
            if (!txtPhoneNum.getText().replaceAll("\\s+", "").isEmpty()) {
                butGetOldLendNote.setDisable(false);
            } else {
                butGetOldLendNote.setDisable(true);
            }
        });

    }

    @FXML
    protected void butGetUserInfoAction(ActionEvent event) {

        // If the txtPhoneNum is empty, return the current user info
        if (txtPhoneNum.getText().isEmpty()) {
            showMessage(
                    Utils.getCurrentUserId(), Utils.getCurrentUserFullName(), Utils.getCurrentUserRole());
        } else {

            String id = txtPhoneNum.getText();
            String role;
            String[] result;
            // If the current user is an Admin, search for the info in both
            // KhachHang and Admin table.
            // If the current user is a KhachHang, search only in KhachHang table.
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

            // Determine if there is a result or not.
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

        int row = Database.populateTable(QueryHelper.getAllLendNote(txtPhoneNum.getText()));

        // If there are no results, the user didn't borrow any book until now.
        if (0 == row) {
            Utils.showAlertOptional("Không tìm thấy phiếu mượn nào.", "Không tìm thấy", Alert.AlertType.INFORMATION);
        } else if (!listenerAdded) {
            // First, set vBoxBksOfLNoteContainer to be managed to show it on
            // the user interface.
            vBoxBksOfLNoteContainer.setManaged(true);

            TableView lendNote = Utils.getTblViewResult();
            // Add the listener for the TableView to 
            // get books in the selected lend note.
            lendNote.getSelectionModel().selectedItemProperty().addListener(e -> {
                listViewBooksOfLendNote.getItems().clear();

                // If the user still not selected any item, do nothing.
                if (!lendNote.getSelectionModel().isEmpty()) {

                    try {
                        String selectedLNote = lendNote.getSelectionModel().getSelectedItem().toString();
                        // Get the Id of selected lend note.
                        String lNoteId = selectedLNote.substring(1, selectedLNote.indexOf(","));

                        lbLendNoteId.setText(lNoteId);
                        // Get all the book belong to selected lend note.
                        ArrayList<String> booksOfLendNote
                                = Database.getAsArrayListOfString(
                                        QueryHelper.getBooksOfLendNote(lNoteId)
                                );
                        // Add the book of selected lend note to the ListView.
                        for (String bk : booksOfLendNote) {
                            listViewBooksOfLendNote.getItems().add(bk);
                        }
                    } catch (StringIndexOutOfBoundsException err) { }

                }

            });
            listenerAdded = true;
        }

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
