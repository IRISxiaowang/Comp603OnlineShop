/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.util.ArrayList;

/**
 * Main model class for the app. Reads/writes from the Database.
 * @author xiaowang
 */
public class ShopModel{
    private static DataBase db;
    private static User currentUser;
    
    public ShopModel(){
        this.db = new DataBase();
        this.db.dbsetup();
        currentUser = null;
    }
    
    /// Updates user's balance in the DB.
    public void updateUserBalance(int userId, double balance){
        db.updateUserBalanceDB(userId, balance);
    }
    
    /// returns the result of search.
    public ArrayList<String[]> searchProduct(String search){
        if(search.length() == 0){
            return getOnSaleProduct();
        } else {
            return db.searchProductDB(search);
        }
    }
    
    /// Gets product by name
    public Product getProduct(String productName){
        return db.getProduct(productName);
    }
    
    /// Gets product owner by name
    public UserSeller getProductOwner(String productName){
        return db.getProductSeller(productName);
    }
    
    /// returns the current user.
    public User getCurrentUser(){
        return currentUser;
    }
    
    /// Registers a new user into the database
    public void registerNewUser(User user){
        db.insertUserDB(user);
    }
    
    /// Performs the Login function in DB. If sucessful, sets the currentUser.
    public void login(String username, String password){
        currentUser = db.login(username, password);
    }
    
    /// Resets the currentUser.
    public void logout(){
        currentUser = null;
    }
    
    /// Checks if user exists.
    public boolean hasUser(String username) {
        return db.hasUser(username);
    }
    
    /// Returns products on sale
    public static ArrayList<String[]> getOnSaleProduct(){
        return db.onSaleProduct();
    }
    
    /// Gets buy history for the current user.
    public static ArrayList<String[]> getBuyerHistoryInfo(){
        return db.transactionBuyerInfo(currentUser.userID);
    }
    
    /// Gets sales history for the current user.
    public static ArrayList<String[]> getSellerHistoryInfo(){
        return db.transactionSellerInfo(currentUser.userID);
    }
    
    /// Checks if product exists.
    public boolean hasProduct(String productName){
        return db.hasProduct(productName);
    }
    
    /// Adds a new product into the DB.
    public void addProduct(Product product){
        db.insertProductDB(product);
    }
    
    /// Adds a new Transaction into the DB.
    public void addTransaction(Transaction transaction){
        db.insertTransactionDB(transaction);
    }
}


