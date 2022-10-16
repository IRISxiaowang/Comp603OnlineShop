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
    
    public void dbsetup() {
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            String tableName = "Shop";

            if (!checkTableExisting(tableName)) {
                statement.executeUpdate("CREATE TABLE " + tableName + " (username VARCHAR(12), password VARCHAR(12), name VARCHAR(12))");
            }
            statement.close();

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
    
    public Data checkNameDB(String username, String password) {
        Data data = new Data();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT username, password, name FROM UserInfo "
                    + "WHERE username = '" + username + "'");
            if (rs.next()) {
                String pass = rs.getString("password");
                System.out.println("***" + pass);
                System.out.println("found user");
                if (password.compareTo(pass) == 0) {
                    
                    data.login = true;
                } else {
                    data.login = false;//*****password wrong
                }
            } else {
                System.out.println("no such user");
                
//                statement.executeUpdate("INSERT INTO UserInfo "
//                        + "VALUES('" + username + "', '" + password + "', 0)");
//               
//                data.login = true;//*****create a new user

            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public void insertDB(String username, String password, String name){
        String tableName = "Shop";
        executeSqlCommand("INSERT INTO " + tableName +
                         " VALUES ('"+ username +"', '"+ password +"', '"+ name + "')");        
    }
    
    public void executeSqlCommand(String command){
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
class Data {
    boolean login = false;
    boolean quit = false;
    User currentUser = null;
   
}