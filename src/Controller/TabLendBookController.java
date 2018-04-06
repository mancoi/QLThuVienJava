/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Database;
import QLThuVien.Book;
import QLThuVien.KhachHang;
import QLThuVien.QueryHelper;
import QLThuVien.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class TabLendBookController implements Initializable {

    //Lend book function tab
    @FXML
    private TextField txtBorrowerId;
    @FXML
    private ListView lstVBorrowBookList;
    @FXML
    private Label lbBorrwerId;
    @FXML
    private Label lbBorrowQuantity;
    @FXML
    private Label lbCheckBrwStatus;
    @FXML
    private Label lbLendBookStatus;
    @FXML
    private Button butCheckBorrower;
    @FXML
    private Button butAddBorrowBook;
    @FXML
    private Button butDelBorrowBook;
    @FXML
    private Button butConfirmBorrowBook;
    @FXML
    private Button butLendBook;

    // Control the book Id(s) user has selected
    private ArrayList<Integer> bkBorrowIds;

    // Store the date of today
    private Calendar now;

    private KhachHang kh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kh = new KhachHang();
        bkBorrowIds = new ArrayList<>();
    }

    @FXML
    protected void butCheckBorrowerAction(ActionEvent event) {

        kh.setPhoneNumber(txtBorrowerId.getText().trim());
        String query = QueryHelper.checkBorrower(kh.getPhoneNumber());

        if (!kh.usernameIsExists()) {
            Utils.showAlertWarn("Người dùng này không tồn tại");
        } else if (!Database.isReturnedAllBook(query)) {
            Database.populateTable(query);
            Utils.showAlertWarn("Không thể cho mượn!\nNgười dùng này vẫn còn phiếu mượn chưa trả");
        } else {
            lbCheckBrwStatus.setText("OK");
            Database.populateTable(QueryHelper.selectAllBooks());
            Utils.enableControls(
                    lstVBorrowBookList, butAddBorrowBook, butDelBorrowBook, butConfirmBorrowBook);
            butCheckBorrower.setDisable(true);
            kh.setNames();
        }
    }

    @FXML
    protected void butAddBorrowBookAction(ActionEvent event) {
        // The ObservableList will contain the content follow this format:
        // [[id, title, author, genre, publisher, year, quantity]]
        ObservableList row = Utils.getTblViewResult().getSelectionModel().getSelectedItems();
        // Add each field to String arry fileds
        String[] fields = row.toString().replaceAll("\\[|\\]", "").replaceAll(", ", ",").split(",");

        if (fields[0].isEmpty()) {
            Utils.showAlertWarn("Hãy chọn một quyển sách trong bảng ở dưới để mượn.");
            return;
        }

        System.out.println(row);
        // Create a Book object
        Book bk = new Book();
        bk.setBookId(Integer.parseInt(fields[0]));
        bk.setTitle(fields[1]);

        // Make sure user can't borrow a book many time
        if (!bkBorrowIds.contains(bk.getBookId())) {
            bkBorrowIds.add(bk.getBookId());
            lstVBorrowBookList.getItems().add(bk);
        } else {
            Utils.showAlertWarn("Không thể mượn một quyển nhiều lần trong một lần mượn!");
        }

    }

    @FXML
    protected void butDelBorrowBook(ActionEvent event) {

        // Because the return string follow this format:
        // [id - title]
        // So we remove the "[", "]" and " - "
        String[] bk = lstVBorrowBookList
                .getSelectionModel()
                .getSelectedItems()
                .toString()
                .replaceAll("\\[|\\]", "")
                .split(" - ");
        // The array contain: bk[0]: id | bk[1]: title
        // If one of its is empty, user are not selected an book
        // to be removed in the ListBox
        if (bk[0].isEmpty()) {
            Utils.showAlertWarn("Hãy chọn một quyển sách trong bảng sách đã mượn để bỏ.");
            return;
        }

        bkBorrowIds.remove(bkBorrowIds.indexOf(Integer.parseInt(bk[0])));
        lstVBorrowBookList.getItems()
                .remove(lstVBorrowBookList.getSelectionModel().getSelectedItem());

    }

    @FXML
    protected void butConfirmBorrowBook(ActionEvent event) {

        // Build the message contain info about the borrower and the books
        // that user are going to borrow
        StringBuilder confirmMes = new StringBuilder();
        confirmMes.append(String.format("Mã người mượn: %s", kh.getPhoneNumber()));
        confirmMes.append(String.format("\nTên người mượn: %s", kh.getFullName()));
        confirmMes.append("\n----------------------");

        int size = lstVBorrowBookList.getItems().size();
        // Get the today info and set the return day to after 20 days
        now = Calendar.getInstance();
        now.add(Calendar.DATE, 20);

        for (int i = 0; i < size; i++) {
            confirmMes.append("\n");
            confirmMes.append(lstVBorrowBookList.getItems().get(i).toString());
        }
        // Show that message, if OK, disable all control in this step
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmMes.toString());
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Utils.disableControls(
                        lstVBorrowBookList, butAddBorrowBook, butDelBorrowBook, butConfirmBorrowBook);
                butLendBook.setDisable(false);

                // Add more information
                lbBorrwerId.setText(kh.getFullName());
                lbBorrowQuantity.setText(String.valueOf(size));
            }
        });

    }

    @FXML
    protected void butLendBookAction(ActionEvent event) {
        now = Calendar.getInstance();
        String today = now.get(Calendar.YEAR)
                + "-" + (now.get(Calendar.MONTH) + 1)
                + "-" + now.get(Calendar.DATE);
        int insrtedNoteId = Database.insertLendNote(QueryHelper.addLendNote(kh.getPhoneNumber(), today));
        if (insrtedNoteId == -1) {
            Utils.showAlertWarn("Có lỗi xảy ra khi thêm phiếu mượn.");
        } else {

            // Build a String follow this format:
            // (bkId1, isrtedId),(bkId2, isrtedId),...
            StringBuilder books = new StringBuilder();
            books.append(String.format("(%d, %d)", bkBorrowIds.get(0), insrtedNoteId));
            for (int i = 1; i < bkBorrowIds.size(); i++) {
                books.append(",");
                books.append(String.format("(%d, %d)", bkBorrowIds.get(i), insrtedNoteId));
            }

            int row = Database.insertData(QueryHelper.addLendBooks(books.toString()));
            if (row == 0) {
                Utils.showAlertWarn("Có lỗi xảy ra khi thêm sách vào danh sách các cuốn sách vừa cho mượn.");
            } else {
                lbLendBookStatus.setText("Cho mượn thành công!");
                butLendBook.setDisable(true);
            }
        }
    }

}
