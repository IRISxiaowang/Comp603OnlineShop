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
    Data data;
    String username;
    
    public ShopModel(){
        this.db = new DataBase();
        this.db.dbsetup();
    }
    
    public void checkName(String username, String password, String name){
        this.username = username;
        this.data = this.db.checkNameDB(username, password);
        if(!data.login){
            db.insertDB(username, password, name);
        }
        this.setChanged();
        this.notifyObservers(data);//*****connect model and view with data(model extends Observerable,view implements Observer)
    }
}


