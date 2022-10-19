/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.util.OptionalInt;

/**
 *
 * @author xiaowang
 */
public class Product {
    public final int productID;
    public final String productName;
    public final double price;
    public final int userID;
//    private OptionalInt buyerID;
    public final String description;
     
    public Product(int productID, int userID, String productName, double price, String description) {// OptionalInt buyerID,
        
        this.userID = userID;
//        this.buyerID = buyerID;
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.description = description;
    }
}
