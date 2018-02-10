/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Database;
import QLThuVien.Admin;
import QLThuVien.KhachHang;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Doctor
 */
public class TabAddUserController implements Initializable {

    //Add user function tab
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField pswPassword;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtFirstName;
    @FXML
    private RadioButton radAddAsAdmin;
    @FXML
    private RadioButton radAddAsGuest;
    @FXML
    Label lbStatusThemThanhVien;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
        //Add user function
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
            Database.populateTable("SELECT * FROM Admin");

        } else {
            KhachHang khachHang = new KhachHang();
            khachHang.setPhoneNumber(userName);
            khachHang.setPassword(pass);
            khachHang.setLastName(lastName);
            khachHang.setFirstName(firstName);

            khachHang.addGuest(lbStatusThemThanhVien);
            Database.populateTable("SELECT * FROM KhachHang");
        }
    }
    
}
