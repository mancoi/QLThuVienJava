/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import QLThuVien.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Doctor
 */
public class ComboBoxData {
    
    public static ArrayList<String> populateCmbBoxTheLoai() {

        final String query = "SELECT TenTheLoai FROM Sach_TheLoai";
        return getData(query);
        
    }
    
    public static ArrayList<String> populateCmbBoxNamXuatBan() {

        final String query = "SELECT DISTINCT NamXuatBan FROM Sach ORDER BY NamXuatBan";
        return getData(query);
        
    }
    
    public static ArrayList<String> populateCmbBoxTacGia() {

        final String query = "SELECT DISTINCT TacGia FROM Sach ORDER BY TacGia";
        return getData(query);
        
    }
    
    private static ArrayList<String> getData(String query) {
        
        ArrayList<String> rs = new ArrayList<>();
        Connection con = null;
        try {
            con = Database.tryToConnect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                rs.add(String.valueOf(resultSet.getObject(1)));
            }
        } catch (SQLException ex) {
            System.err.println("ComboBoxData: " + ex.getMessage());
        }
        finally {
            if (null != con) {
                try {
                    con.close();
                } catch (SQLException ex) { }
            }
        }
        
        return rs;
    }
    
}
