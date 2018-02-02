/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLThuVien;

import Data.Database;

/**
 *
 * @author Doctor
 */
public class Admin {

    private String username;
    private String password;
    private String lastName;
    private String firstName;

    public Admin(String usrnm, String pass) {
        username = usrnm;
        password = pass;
    }

    public boolean isValid() {
        String[] result = Database.queryLogInStatement("Admin", "username='" + username + "'");

        if (null == result || !result[1].equals(password)) {
            return false;
        }
        //Set lastName and firstName if this is a valid user
        lastName = result[2];
        firstName = result[3];

        return true;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
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
