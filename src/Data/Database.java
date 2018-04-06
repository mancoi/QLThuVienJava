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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
            con = DriverManager.getConnection(Utils.getDATABASE_CONNECTION_STRING());
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

    /**
     * This method return an array contain KhachHang LastName and FirstName, if
     * there are nothing, return an null array.
     *
     * @param query
     * @return
     */
    public static String[] getUserName(String query) {
        String[] rs = null;
        try {
            Database.tryToConnect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //If there's no result, don't do this
            if (resultSet.next()) {
                rs = new String[2];
                rs[0] = resultSet.getString(1);
                rs[1] = resultSet.getString(2);
            }

        } catch (SQLException ex) {
            System.err.println("DatabaseClass: " + ex.getMessage());
            return rs;
        }
        return rs;
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
                rs[0] = String.valueOf(resultSet.getObject(1));
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

    public static int insertData(String query) {
        int rows = 0;
        try {

            Statement statement = con.createStatement();
            rows = statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }

    public static int insertLendNote(String query) {
        int id = -1;
        try {

            Statement statement = con.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public static boolean isConflictId(String query) {
        boolean isConflict = false;
        try {

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            isConflict = resultSet.next();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isConflict;
    }

    public static boolean isReturnedAllBook(String query) {
        boolean isNotReturnedAll = false;
        try {

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //If the user is not return all books, this statement will return true
            isNotReturnedAll = resultSet.next();

        } catch (SQLException ex) {
            System.err.printf("Database.isReturnedAllBook bị lỗi\n%s", ex.getMessage());
        }

        return !isNotReturnedAll;

    }

    public static String[] getLendNoteInfo(String query) {
        String[] rs = null;
        try {
            Database.tryToConnect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //If there's no result, don't do this
            if (resultSet.next()) {
                rs = new String[2];
                rs[0] = String.valueOf(resultSet.getInt(1));
                rs[1] = resultSet.getString(2);
            }

        } catch (SQLException ex) {
            System.err.println("DatabaseClass: " + ex.getMessage());
            return rs;
        }

        return rs;
    }

    public static ArrayList<String> getAsArrayListOfString(String query) {
        ArrayList<String> rs = new ArrayList<>();
        try {
            Database.tryToConnect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //If there's no result, don't do this
            while (resultSet.next()) {
                rs.add(resultSet.getString(1));
            }

        } catch (SQLException ex) {
            System.err.println("DatabaseClass: " + ex.getMessage());
            return rs;
        }

        return rs;
    }

    public static int getGenreOrPublsherId(String query) {

        int id = -1;
        try {
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return id;
        }
        return id;
    }

    public static boolean publisherIsExist(String query) {
        boolean isExists = false;
        try {
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                isExists = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return isExists;
        }
        return isExists;
    }

    public static String getCountResultAsString(String query) {
        String count = null;
        try {
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                count = resultSet.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return count;
        }
        return count;
    }

    public static int getCountResultAsInt(String query) {
        int count = -1;
        try {
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return count;
        }
        return count;
    }

    public static int populateTable(String query) {

        TableView tblViewResult = Utils.getTblViewResult();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        //Reset the TableView first
        tblViewResult.getColumns().clear();
        tblViewResult.getItems().clear();
        int rows = 0;
        try {

            //ResultSet
            ResultSet rs = con.createStatement().executeQuery(query);

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY ********************************
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
             * Data added to ObservableList ******************************
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
                rows++;
            }

            //FINALLY ADDED TO TableView
            tblViewResult.setItems(data);
        } catch (Exception e) {
            System.out.println("Error on Building Data " + e.getMessage());
        }
        return rows;
    }

}
