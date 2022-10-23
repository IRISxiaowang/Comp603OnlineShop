/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

/**
 *
 * @author xiaowang
 */
    enum Role {
    BUYER,
    SELLER;
    
    public String toString(){
        switch(this) {
            case BUYER:
                return "Buyer";
            case SELLER:
                return "Seller";
        }
        return "";
    }
    
    public int toInt(){
        return this.ordinal();
    }
}

public abstract class User {
    public final int userID;
    public final String username;
    public final String password;
    public final String name;
    public final String address;
    public final String phone;
    public final String email;
    public final String bankAccount;
    public final Role role;
    public double balance;

    public User(int userID, String username, String password, String name, String address, String phone, String email, String bankAccount, Role role, double balance) {
        this.userID = userID;
        this.username = username;   //Winter: This is username for login
        this.password = password;
        this.name = name;           //Winter: This is real name
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.bankAccount = bankAccount;
        this.role = role;
        this.balance = balance;
    }
    
    public String toString() {
        return "" +
                "ID:            " + userID + "\n" +
                "Username:      " + username + "\n" +
                "Password:      " + password + "\n" +
                "Name:          " + name + "\n" +
                "Address:       " + address + "\n" +
                "Phone:         " + phone + "\n" +
                "Email:         " + email + "\n" +
                "Bank Account:  " + bankAccount + "\n"+
                "Role:          " + role + "\n"+
                "Balance:       " + balance;
        
    }
}
