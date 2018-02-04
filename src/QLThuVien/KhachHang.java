/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLThuVien;

import Data.Database;
import javafx.scene.control.Label;

/**
 *
 * @author Doctor
 */
public class KhachHang {

    private String phoneNumber;
    private String password;
    private String lastName;
    private String firstName;

    public KhachHang() {}
    
    public KhachHang(String phoneNum, String pass) {
        phoneNumber = phoneNum;
        password = pass;
    }

    public boolean isValid() {

        String[] result = Database.queryLogInStatement("KhachHang", "phoneNumber='" + phoneNumber + "'");

        if (null == result || !result[1].equals(password)) {
            return false;
        }
        //Set lastName and firstName if this is a valid user
        lastName = result[2];
        firstName = result[3];

        return true;

    }

    public void addGuest(Label lbstatus){
        
        //Check the username first to make sure it not exists
        if (!usernameIsOK()) {
            return;
        }
        
        String[] params = {phoneNumber, password, lastName, firstName};
        int rows = Database.insertData(QueryHelper.addUser(params, "KhachHang"));
        
        if (rows > 0) {
            lbstatus.setText("Thêm thành công");
        }
        else {
            lbstatus.setText("Thêm thất bại");
        }
    }
    
    private boolean usernameIsOK() {
        
        if (null == phoneNumber) {
            return false;
        }
        
        String query = "SELECT * FROM KhachHang WHERE phoneNumber='" + phoneNumber + "'";
        if (Database.isConflictId(query)) {
            Utils.showAlert("Trùng số điện thoại, người dùng đã tồn tại");
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        try {
            int phoneNum = Integer.parseInt(phoneNumber);
            this.phoneNumber = String.valueOf(phoneNum);
        } catch (NumberFormatException ex) {
            Utils.showAlert("Mã khách hàng phải là một chuỗi số!\n" + ex.getMessage());
        }
        
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
