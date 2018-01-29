/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLThuVien;

/**
 *
 * @author Doctor
 */
public class KhachHang {

    private String phoneNumber;
    private String password;
    private String lastName;
    private String firstName;

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
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
