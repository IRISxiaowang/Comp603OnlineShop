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
    //private Product product;
    
    public ShopModel(){
        this.db = new DataBase();
        this.db.dbsetup();
        currentUser = null;
    }
    
    public User getCurrentUser(){
        return currentUser;
    }
    
    public void registerNewUser(User user){
        db.insertDB(user);
    }
    
    public void login(String username, String password){
        this.currentUser = this.db.login(username, password);
    }
    
    public boolean hasUser(String username) {
        return db.hasUser(username);
    }
    
    public static ArrayList<String[]> getProductInfo(){
        return db.productInfo(currentUser);
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
    
    public boolean isProductSold(int productID){
        return db.isProductSold(productID);
    }
}


