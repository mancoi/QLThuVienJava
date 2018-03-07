/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLThuVien;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.TableView;

/**
 *
 * @author Doctor
 */
public class Utils {

    private static String currentUserId;
    private static String currentUserRole;

    private static KhachHang currentKh;
    private static Admin currentAd;
    
    private static TableView tblViewResult;
    
    private static final String DATABASE_CONNECTION_STRING = 
            "jdbc:sqlserver://localhost;databaseName=QLThuVien;username=sa";

    public static void setCurrentUser(String userId, String role) {
        currentUserId = userId;
        currentUserRole = role;
    }

    public static void setCurrentUser(KhachHang kh) {
        setCurrentKh(kh);
        setCurrentUserId(getCurrentKh().getPhoneNumber());
        setCurrentUserRole("KhachHang");
    }

    public static void setCurrentUser(Admin ad) {
        setCurrentAd(ad);
        setCurrentUserId(getCurrentAd().getUsername());
        setCurrentUserRole("Admin");
    }

    public static String getCurrentUserFullName() {
        StringBuilder fullName = new StringBuilder();
        if (getCurrentUserRole().equals("Admin")) {
            fullName.append(getCurrentAd().getLastName());
            fullName.append(" ");
            fullName.append(getCurrentAd().getFirstName());
        } else {
            fullName.append(getCurrentKh().getLastName());
            fullName.append(" ");
            fullName.append(getCurrentKh().getFirstName());
        }
        return fullName.toString();
    }

    public static void disableControls(Control ctrl, Control ctrl1, Control ctrl2, Control ctrl3) {
        ctrl.setDisable(true);
        ctrl1.setDisable(true);
        ctrl2.setDisable(true);
        ctrl3.setDisable(true);
    }
    
    public static void enableControls(Control ctrl, Control ctrl1, Control ctrl2, Control ctrl3) {
        ctrl.setDisable(false);
        ctrl1.setDisable(false);
        ctrl2.setDisable(false);
        ctrl3.setDisable(false);
    }
    
    public static void showAlertWarn(String mes) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mes);
        alert.show();
    }
    
    public static void showAlertOptional(
            String mes
            , String title
            , Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, mes);
        alert.setHeaderText(title);
        alert.show();
    }
    
    public static boolean currentUserIsAdmin() {
        return currentUserRole.equals("Admin");
    }
    
    public static void confirmExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn muốn thoát?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();
            }
        });
    }

    public static void reset() {
        currentUserId = null;
        currentUserRole = null;
        currentKh = null;
        currentAd = null;
    }

    /**
     * @return the currentUserId
     */
    public static String getCurrentUserId() {
        return currentUserId;
    }

    /**
     * @param aCurrentUser the currentUserId to set
     */
    public static void setCurrentUserId(String aCurrentUser) {
        currentUserId = aCurrentUser;
    }

    /**
     * @return the currentUserRole
     */
    public static String getCurrentUserRole() {
        return currentUserRole;
    }

    /**
     * @param aCurrentUserRole the currentUserRole to set
     */
    public static void setCurrentUserRole(String aCurrentUserRole) {
        currentUserRole = aCurrentUserRole;
    }

    /**
     * @return the currentKh
     */
    public static KhachHang getCurrentKh() {
        return currentKh;
    }

    /**
     * @param aCurrentKh the currentKh to set
     */
    public static void setCurrentKh(KhachHang aCurrentKh) {
        currentKh = aCurrentKh;
    }

    /**
     * @return the currentAd
     */
    public static Admin getCurrentAd() {
        return currentAd;
    }

    /**
     * @param aCurrentAd the currentAd to set
     */
    public static void setCurrentAd(Admin aCurrentAd) {
        currentAd = aCurrentAd;
    }

    /**
     * @return the DATABASE_CONNECTION_STRING
     */
    public static String getDATABASE_CONNECTION_STRING() {
        return DATABASE_CONNECTION_STRING;
    }

    /**
     * @return the tblViewResult
     */
    public static TableView getTblViewResult() {
        return tblViewResult;
    }

    /**
     * @param aTblViewResult the tblViewResult to set
     */
    public static void setTblViewResult(TableView aTblViewResult) {
        tblViewResult = aTblViewResult;
    }
}
