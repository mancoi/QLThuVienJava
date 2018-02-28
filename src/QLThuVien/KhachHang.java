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

    public KhachHang() {
    }

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

    /**
     * Get the last name and the first name of any KhachHang that called this
     * method then set it to current KhachHang, before use this method, please
     * make sure the current KhachHang is a valid user.
     */
    public void setNames() {
        String query
                = "SELECT Ho, Ten FROM KhachHang Where phoneNumber = " + phoneNumber;
        String[] names = Database.getKHName(query);

        if (names != null) {
            lastName = names[0];
            firstName = names[1];
        } else {
            Utils.showAlert("Mã người dùng không tồn tại.");
        }

    }

    public void addGuest(Label lbstatus) {

        //Check the username first to make sure it not exists
        if (usernameIsExists()) {
            Utils.showAlert("Trùng số điện thoại, người dùng đã tồn tại");
            return;
        }

        String[] params = {phoneNumber, password, lastName, firstName};
        int rows = Database.insertData(QueryHelper.addUser(params, "KhachHang"));

        if (rows > 0) {
            lbstatus.setText("Thêm thành công");
        } else {
            lbstatus.setText("Thêm thất bại");
        }
    }

    //Check if the user exist or not
    public boolean usernameIsExists() {

        if (null == phoneNumber) {
            return false;
        }

        String query = "SELECT * FROM KhachHang WHERE phoneNumber='" + phoneNumber + "'";
        return Database.isConflictId(query);
    }

    /**
     * @return FullName
     */
    public String getFullName() {
        return lastName + " " + firstName;
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
