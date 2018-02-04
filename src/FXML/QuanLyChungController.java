/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXML;

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
                    Utils.enableControls(txtBookTitleToSearch, cmbBoxBookAuthorToSearch, cmbBoxBookPublishYearToSearch, cmbBoxBookGenreToSearch);
                } else {
                    Utils.disableControls(txtBookTitleToSearch, cmbBoxBookAuthorToSearch, cmbBoxBookPublishYearToSearch, cmbBoxBookGenreToSearch);
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
        Book book = new Book();
        try {

            //If user not enter a bookId, assign it to -1
            String bId = txtBookIDToSearch.getText().trim();
            book.setBookId(bId.isEmpty() ? -1 : Integer.parseInt(bId));
            //Get data from another field
            book.setTitle(txtBookTitleToSearch.getText().trim());
            book.setAuthor(cmbBoxBookAuthorToSearch.getEditor().getText().trim());
            book.setGenre(cmbBoxBookGenreToSearch.getEditor().getText().trim());
            book.setYearPublish(cmbBoxBookPublishYearToSearch.getEditor().getText().trim());
            //Perform search
            rows = book.searchBook(tblViewResult);

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
    protected void butAddUserAction(ActionEvent event) {

        String userName = txtUsername.getText().trim();
        String pass = pswPassword.getText().trim();
        String lastName = txtLastname.getText().trim();
        String firstName = txtFirstName.getText().trim();

        //Because every filed is required, not allow user to left any empty
        if (userName.isEmpty() || pass.isEmpty() || lastName.isEmpty() || firstName.isEmpty()) {
            lbStatusThemThanhVien.setText("Không được để trống bất kỳ trường nào");
            return;
        }

        if (radAddAsAdmin.isSelected()) {
            Admin admin = new Admin();
            admin.setUsername(userName);
            admin.setPassword(pass);
            admin.setLastName(lastName);
            admin.setFirstName(firstName);

            admin.addAdmin(lbStatusThemThanhVien);
            Database.populateTable("SELECT * FROM Admin", tblViewResult);
            
        } else {
            KhachHang khachHang = new KhachHang();
            khachHang.setPhoneNumber(userName);
            khachHang.setPassword(pass);
            khachHang.setLastName(lastName);
            khachHang.setFirstName(firstName);
            
            khachHang.addGuest(lbStatusThemThanhVien);
            Database.populateTable("SELECT * FROM KhachHang", tblViewResult);
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
    //SearchBook function tab
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
    //Add user function tab
    @FXML
    TextField txtUsername;
    @FXML
    TextField pswPassword;
    @FXML
    TextField txtLastname;
    @FXML
    TextField txtFirstName;
    @FXML
    private RadioButton radAddAsAdmin;
    @FXML
    private RadioButton radAddAsGuest;
    @FXML
    Label lbStatusThemThanhVien;
    //TableView
    @FXML
    private TableView tblViewResult;
}
