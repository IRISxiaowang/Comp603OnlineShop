/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author xiaowang
 */
public class ShopModel extends Observable{
    private static DataBase db;
    private static User currentUser;
    
    public ShopModel(){
        this.db = new DataBase();
        this.db.dbsetup();
        currentUser = null;
    }
    public void updateUserBalance(int userId, double balance){
        db.updateUserBalanceDB(userId, balance);
    }
    public ArrayList<String[]> searchProduct(String search){
        if(search.length() == 0){
            return getOnSaleProduct();
        } else {
            return db.searchProductDB(search);
        }
    }
    
    public Product getProduct(String productName){
        return db.getProduct(productName);
    }
    
    public UserSeller getProductOwner(String productName){
        return db.getProductSeller(productName);
    }
    
    public User getCurrentUser(){
        return currentUser;
    }
    
    public void registerNewUser(User user){
        db.insertUserDB(user);
    }
    
    public void login(String username, String password){
        this.currentUser = this.db.login(username, password);
    }
    
    public boolean hasUser(String username) {
        return db.hasUser(username);
    }
    
    public static ArrayList<String[]> getOnSaleProduct(){
        return db.onSaleProduct();
    }
    
    public static ArrayList<String[]> getBuyerHistoryInfo(){
        return db.transactionBuyerInfo(currentUser.userID);
    }
    
    public static ArrayList<String[]> getSellerHistoryInfo(){
        return db.transactionSellerInfo(currentUser.userID);
    }
    
    public boolean hasProduct(String productName){
        return db.hasProduct(productName);
    }
        
    public void addProduct(Product product){
        db.insertProductDB(product);
    }
    
    public void addTransaction(Transaction transaction){
        db.insertTransactionDB(transaction);
    }
}


