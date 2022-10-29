/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.time.LocalDate;

/**
 *
 * @author xiaowang
 */
public class Transaction {

    private int productID;
    private int buyerID;
    private int sellerID;
    private String buyerAccount;
    private String sellerAccount;
    private LocalDate date;

    public Transaction(int productID, int buyerId, String buyerAccount, int sellerId, String sellerAccount, LocalDate date){
        this.buyerAccount = buyerAccount;
        this.buyerID = buyerId;
        this.date = date;
        this.productID = productID;
        this.sellerAccount = sellerAccount;
        this.sellerID = sellerId;
    }
 
    /**
     * @return the productID
     */
    public int getProductID() {
        return productID;
    }

    /**
     * @return the buyerID
     */
    public int getBuyerID() {
        return buyerID;
    }

    /**
     * @return the sellerID
     */
    public int getSellerID() {
        return sellerID;
    }

    /**
     * @return the buyerAccount
     */
    public String getBuyerAccount() {
        return buyerAccount;
    }

    /**
     * @return the sellerAccount
     */
    public String getSellerAccount() {
        return sellerAccount;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }
            
    
}
