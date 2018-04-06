/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Database;
import QLThuVien.QueryHelper;
import QLThuVien.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

    @FXML
    private MenuItem menuButLogout;
    @FXML
    private Tab tabThongTinDocGia;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Set the tableView
        Utils.setTblViewResult(tblViewResult);

        // Remove not neccessary Tabs if the current user is a KhachHang
        if (Utils.getCurrentUserRole().equals("KhachHang")) {
            tabPaneQuanLy.getTabs().remove(tabThemThanhVien);
            tabPaneQuanLy.getTabs().remove(tabMuonSach);
            tabPaneQuanLy.getTabs().remove(tabThemSach);
            tabPaneQuanLy.getTabs().remove(tabThongKe);
            tabPaneQuanLy.getTabs().remove(tabTraSach);
        }

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
    private Tab tabThemThanhVien;
    @FXML
    private Tab tabThemSach;
    @FXML
    private Tab tabMuonSach;
    @FXML
    private Tab tabTraSach;
    @FXML
    private Tab tabThongKe;
    @FXML
    private MenuButton menuButtonUsername;

    //TableView
    @FXML
    private TableView tblViewResult;

    @FXML
    private void menuAboutAction(ActionEvent event) {
        String mes
                = "Đề tài: Quản lý thư viện\n"
                + "1451010099 - Trần Thành Long\n1451010104 - Phạm Cao Mẫn";
        String title = "Bài tập lớn môn Lập trình Java";
        Utils.showAlertOptional(mes, title, Alert.AlertType.INFORMATION);
    }
}
