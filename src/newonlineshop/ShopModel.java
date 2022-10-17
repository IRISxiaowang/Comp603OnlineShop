/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.util.Observable;

/**
 *
 * @author xiaowang
 */
public class ShopModel extends Observable{
    DataBase db;
    User currentUser;
    
    public ShopModel(){
        this.db = new DataBase();
        this.db.dbsetup();
        currentUser = null;
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
}


