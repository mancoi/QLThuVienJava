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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

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

    public static void populateTable(String query, TableView tblViewResult) {

        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        try {

            //ResultSet
            ResultSet rs = con.createStatement().executeQuery(query);

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY 
             * ********************************
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblViewResult.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }

            /**
             * ******************************
             * Data added to ObservableList 
             * ******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tblViewResult.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

}
