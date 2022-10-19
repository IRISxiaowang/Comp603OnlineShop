/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xiaowang
 */
public class DataBase {

    Connection conn = null;
    String url = "jdbc:derby:OnlineShopDB;create=true";
    String dbusername = "pdc";
    String dbpassword = "pdc";
    public final String USER_TABLE_NAME = "ShopUser";
    public final String PRODUCT_TABLE_NAME = "Product";//
    public void dbsetup() {
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            if (!checkTableExisting(USER_TABLE_NAME)) {
                executeSqlUpdate("CREATE TABLE " + USER_TABLE_NAME + " (userId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), username VARCHAR(30), password VARCHAR(30), name VARCHAR(30), bankaccount VARCHAR(30), address VARCHAR(30), phone VARCHAR(30), email VARCHAR(30), role INT)");
            }
            if (!checkTableExisting(PRODUCT_TABLE_NAME)) {
                executeSqlUpdate("CREATE TABLE " + PRODUCT_TABLE_NAME + " (productID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), userId INT, productName VARCHAR(30), price DOUBLE, description VARCHAR(30))");
            }
        } catch (Throwable e) {
            System.out.println(e);

        }
    }

    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {
            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            //Statement dropStatement=null;
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + "  is there");
                    flag = true;
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
        } catch (SQLException ex) {
        }
        return flag;
    }

    /// Checks if the username and password exists in the database
    public User login(String username, String password) {
        User user = null;

        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE username = '" + username + "'";
        try {
            ResultSet rs = executeSqlQuery(query);
            if (rs.next()) {
                String pass = rs.getString("password");
                if (password.equals(pass)) {
                    int role = rs.getInt("role");
                    if (role == 0) {
                        user = new UserBuyer(
                                rs.getInt("userid"),
                                username,
                                password,
                                rs.getString("name"),
                                rs.getString("address"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getString("bankaccount")
                        );
                    } else if (role == 1) {
                        user = new UserSeller(
                                rs.getInt("userid"),
                                username,
                                password,
                                rs.getString("name"),
                                rs.getString("address"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getString("bankaccount")
                        );
                    }
                    rs.close();
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return user;
    }

    public boolean hasUser(String username) {
        String query = "SELECT username FROM " + USER_TABLE_NAME + " WHERE username = '" + username + "'";
        ResultSet rs = executeSqlQuery(query);
        try {
            boolean hasUser = rs.next();
            rs.close();
            return hasUser;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean hasProduct(String productName){
        String query = "SELECT productName FROM " + PRODUCT_TABLE_NAME + " WHERE productName = '" + productName + "'";
        ResultSet rs = executeSqlQuery(query);
        try {
            boolean hasUser = rs.next();
            rs.close();
            return hasUser;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    public void insertDB(User user) {
        // username , password, name, bankaccount, address, phone, email, role
        executeSqlUpdate("INSERT INTO " + USER_TABLE_NAME
                + " (username, password, name, bankaccount, address, phone, email, role)"
                + " VALUES ('" + user.username + "', '" + user.password + "', '" + user.name + "', '" + user.bankAccount + "', '" + user.address + "', '" + user.phone + "', '" + user.email + "', " + user.role.toInt() + ")");
    }
    
    public void insertProductDB(Product product) {//19
        
        executeSqlUpdate("INSERT INTO " + PRODUCT_TABLE_NAME
                + " (productName, price, description)"
                + " VALUES ('" + product.userID +"', '" + product.productName + "', '" + product.price + "', '" + product.description + ")");
    }

    public ArrayList<String[]> productInfo(User user){
        String query = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE userId = " + user.userID;
        ArrayList<String[]> res = new ArrayList<>();
        try {
            ResultSet rs = executeSqlQuery(query);
            while(rs.next()) {
                String[] row = {rs.getString("productName"), rs.getString("price"), rs.getString("description")};
                res.add(row);
            }
            } catch (SQLException e) {
            System.out.println(e);
        }
        return res;
    }
    public void executeSqlUpdate(String command) {
        System.out.print("SQL Command: <" + command + ">");

        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(command);
            System.out.println("SQL command Completed.");
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, "", ex);
        }
    }

    public ResultSet executeSqlQuery(String query) {
        System.out.print("SQL Query: <" + query + ">");
        ResultSet rs = null;
        Statement statement;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            System.out.println("SQL query Completed.");
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, "", ex);
        }
        return rs;
    }

//  void quitDB(String username, int score) {
//
//        Statement statement;
//        try {
//            statement = conn.createStatement();
//            statement.executeUpdate("UPDATE UserInfo SET score=" + score + " WHERE userid='" + username + "'");
//            System.out.println(username + score);
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
}
