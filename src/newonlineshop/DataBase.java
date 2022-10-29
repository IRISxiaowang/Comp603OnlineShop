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
 * This class contains read/writes to the Databases.
 *
 * @author xiaowang
 */
public class DataBase {

    Connection conn = null;
    String url = "jdbc:derby:OnlineShopDB;create=true;";
    String dbusername = "pdc";
    String dbpassword = "pdc";
    public final String USER_TABLE_NAME = "ShopUser";
    public final String PRODUCT_TABLE_NAME = "Product";
    public final String TRANSACTION_TABLE_NAME = "ProductTransaction";

    // Sets up the database using the Production URL
    public void dbsetup() {
        dbsetup(url);
    }

    // Sets up database using the given URL.
    public void dbsetup(String dbUrl) {
        try {
            // Creates the tables if non exists.
            conn = DriverManager.getConnection(dbUrl, dbusername, dbpassword);
            if (!checkTableExisting(USER_TABLE_NAME)) {
                executeSqlUpdate("CREATE TABLE " + USER_TABLE_NAME + " (userId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), username VARCHAR(30), password VARCHAR(30), name VARCHAR(30), bankaccount VARCHAR(30), address VARCHAR(30), phone VARCHAR(30), email VARCHAR(30), role INT, balance DOUBLE)");
            }
            if (!checkTableExisting(PRODUCT_TABLE_NAME)) {
                executeSqlUpdate("CREATE TABLE " + PRODUCT_TABLE_NAME + " (productID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), userId INT, productName VARCHAR(30), price DOUBLE, description VARCHAR(30))");
            }
            if (!checkTableExisting(TRANSACTION_TABLE_NAME)) {
                executeSqlUpdate("CREATE TABLE " + TRANSACTION_TABLE_NAME + " (productID INT, buyerId INT, buyerAccount VARCHAR(30), sellerId INT, sellerAccount VARCHAR(30), date DATE)");//
            }
        } catch (Throwable e) {
            System.out.println(e);

        }
    }

    /// Check if a table already exists.
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

    /// Retrieives the User (can be buyer or seller) using Useranme, and the correct password.
    /// If the user doesn't exists or the password is wrong, null is returned.
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
                                rs.getString("bankaccount"),
                                rs.getDouble("balance")
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
                                rs.getString("bankaccount"),
                                rs.getDouble("balance")
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

    /// Updates the balacne of a user to the given value.
    public void updateUserBalanceDB(int userId, double balance) {
        executeSqlUpdate("UPDATE " + USER_TABLE_NAME + " SET balance = " + balance + " WHERE userId = " + userId);
    }

    /// Checks if such user exists.
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

    /// Checks if such product exists
    public boolean hasProduct(String productName) {
        String query = "SELECT productName FROM " + PRODUCT_TABLE_NAME + " WHERE productName = '" + productName + "'";
        ResultSet rs = executeSqlQuery(query);
        try {
            boolean hasProduct = rs.next();
            rs.close();
            return hasProduct;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    /// Insert a user into the Database.
    public void insertUserDB(User user) {
        // username , password, name, bankaccount, address, phone, email, role
        executeSqlUpdate("INSERT INTO " + USER_TABLE_NAME
                + " (username, password, name, bankaccount, address, phone, email, role)"
                + " VALUES ('" + user.username + "', '" + user.password + "', '" + user.name + "', '" + user.bankAccount + "', '" + user.address + "', '" + user.phone + "', '" + user.email + "', " + user.role.toInt() + ")");
    }

    /// Insert a new product into the database
    public void insertProductDB(Product product) {

        executeSqlUpdate("INSERT INTO " + PRODUCT_TABLE_NAME
                + " (userId, productName, price, description)"
                + " VALUES (" + product.userID + ", '" + product.productName + "', " + product.price + ", '" + product.description + "')");
    }

    /// Insert a new transaction in the database
    public void insertTransactionDB(Transaction transaction) {//19
        //TRANSACTION_TABLE_NAME + " (productID INT, buyerId INT, buyerAccount VARCHAR(30), sellerId INT, sellerAccount VARCHAR(30), date DATE)");

        executeSqlUpdate("INSERT INTO " + TRANSACTION_TABLE_NAME
                + " VALUES (" + transaction.getProductID() + ", " + transaction.getBuyerID() + ", '" + transaction.getBuyerAccount() + "', " + transaction.getSellerID() + ", '" + transaction.getSellerAccount() + "', '" + transaction.getDate().toString() + "')");
    }

    /// Get product information for all items currently on sale.
    public ArrayList<String[]> onSaleProduct() {
        String query = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE productID NOT IN ( SELECT productID FROM ProductTransaction)";
        ArrayList<String[]> res = new ArrayList<>();
        try {
            ResultSet rs = executeSqlQuery(query);
            while (rs.next()) {
                String[] row = {rs.getString("productName"), rs.getString("price"), rs.getString("description")};
                res.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return res;
    }

    /// Search on sale products that contains the search string
    public ArrayList<String[]> searchProductDB(String search) {
        String query = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE productName LIKE '%" + search + "%' AND productID NOT IN ( SELECT productID FROM ProductTransaction)";
        ArrayList<String[]> res = new ArrayList<>();
        try {
            ResultSet rs = executeSqlQuery(query);
            while (rs.next()) {
                String[] row = {rs.getString("productName"), rs.getString("price"), rs.getString("description")};
                res.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return res;
    }

    /// Gets info for all product bought by a buyer.
    public ArrayList<String[]> transactionBuyerInfo(int userID) {
        String query = "SELECT productName, description, price, username, date FROM "
                + PRODUCT_TABLE_NAME + " JOIN " + TRANSACTION_TABLE_NAME
                + " ON " + PRODUCT_TABLE_NAME + ".productID = " + TRANSACTION_TABLE_NAME + ".productID"
                + " JOIN " + USER_TABLE_NAME
                + " ON " + TRANSACTION_TABLE_NAME + ".sellerId = " + USER_TABLE_NAME + ".userId"
                + " WHERE buyerId = " + userID;
        ArrayList<String[]> res = new ArrayList<>();
        try {
            ResultSet rs = executeSqlQuery(query);
            while (rs.next()) {
                String[] row = {rs.getString("productName"), rs.getString("description"), rs.getString("price"), rs.getString("username"), rs.getString("date")};
                res.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return res;
    }
    
    /// Gets info on all products sold by a seller.
    public ArrayList<String[]> transactionSellerInfo(int userID) {
        String query = "SELECT productName, description, price, username, date FROM "
                + PRODUCT_TABLE_NAME + " JOIN " + TRANSACTION_TABLE_NAME
                + " ON " + PRODUCT_TABLE_NAME + ".productID = " + TRANSACTION_TABLE_NAME + ".productID"
                + " JOIN " + USER_TABLE_NAME
                + " ON " + TRANSACTION_TABLE_NAME + ".buyerId = " + USER_TABLE_NAME + ".userId"
                + " WHERE sellerId = " + userID;
        ArrayList<String[]> res = new ArrayList<>();
        try {
            ResultSet rs = executeSqlQuery(query);
            while (rs.next()) {
                String[] row = {rs.getString("productName"), rs.getString("description"), rs.getString("price"), rs.getString("username"), rs.getString("date")};
                res.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return res;
    }
    
    /// Gets a product by name
    public Product getProduct(String productName) {
        String query = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE productName = '" + productName + "'";
        Product soldProduct = null;
        try {
            ResultSet rs = executeSqlQuery(query);
            while (rs.next()) {
                soldProduct = new Product(rs.getInt("productID"), rs.getInt("userId"), rs.getString("productName"), rs.getDouble("price"), rs.getString("description"));

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return soldProduct;
    }
    
    /// Gets the seller of a product
    public UserSeller getProductSeller(String productName) {
        String query = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE productName = '" + productName + "'";
        int ownerId = 0;
        UserSeller owner = null;
        try {
            ResultSet rs = executeSqlQuery(query);
            if (rs.next()) {
                ownerId = rs.getInt("userId");
            }
            String queryUser = "SELECT * FROM " + USER_TABLE_NAME + " WHERE userId = " + ownerId;
            ResultSet rsUser = executeSqlQuery(queryUser);
            if (rsUser.next()) {
                owner = new UserSeller(ownerId, rsUser.getString("username"), rsUser.getString("password"), rsUser.getString("name"), rsUser.getString("address"), rsUser.getString("phone"), rsUser.getString("email"), rsUser.getString("bankaccount"), rsUser.getDouble("balance"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return owner;
    }
    
    /// Execute an Update. Handles any exceptions.
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
    
    /// Executes a Query. Return the result. Handles any exceptions.
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
}
