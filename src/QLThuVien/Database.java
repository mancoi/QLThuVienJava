/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLThuVien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Doctor
 */
public class Database {

    private static Connection con;

    public static Connection tryToConnect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=QLThuVien;username=sa");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Kết nối thất bại " + ex.getMessage());
        }

        return con;
    }

    public static String testConnection() {
        con = Database.tryToConnect();
        try {
            con.isValid(10);
        } catch (Exception ex) {
            return "Kết nối thất bại " + ex.getMessage();
        }
        return "Kết nối CSDL thành công";
    }

    public static String[] queryLogInStatement(String selectFrom, String whereToSelect) {

        String[] rs = null; //If the query return nothing, return a null String[]

        try {
            Database.tryToConnect();
            Statement statement = con.createStatement();
            String query = String.format("SELECT * FROM %s WHERE %s", selectFrom, whereToSelect);
            ResultSet resultSet = statement.executeQuery(query);
            //If there's no result, don't do this
            if (resultSet.next()) {
                rs = new String[4];
                rs[0] = resultSet.getString(1);
                rs[1] = resultSet.getString(2);
                rs[2] = resultSet.getString(3);
                rs[3] = resultSet.getString(4);
            }

        } catch (SQLException ex) {
            System.err.println("DatabaseClass: " + ex.getMessage());
            return rs;
        }

        return rs;
    }

}
