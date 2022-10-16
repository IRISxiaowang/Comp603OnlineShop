/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.util.HashMap;
import java.util.Optional;

/**
 *
 * @author xiaowang
 */
public class UserBuyer extends User {
    private double moneySpent;
    
    public static HashMap<Integer, UserBuyer> buyers = FileIO.ReadBuyers(); // < User id & User instant >

    public UserBuyer(int userID, String username, String password, String name, String address, long phone, String email, String bankAccount) {
        super(userID, username, password, name, address, phone, email, bankAccount, Role.BUYER);
        moneySpent = 0;
    }
    
    public double getMoneySpent(){
        return moneySpent;
    }
    
    public void spent(double amount){
        moneySpent += amount;
    }
    public static Optional<UserBuyer> GetBuyer(String username) {
        return buyers.values().stream().filter(user -> user.username.equals(username)).findFirst();
    }

    public static Optional<UserBuyer> GetBuyer(int userID) {
        UserBuyer buyer = buyers.get(userID);
        if(buyer == null) {
            return Optional.empty();
        } else {
            return Optional.of(buyer);
        }
    }

    public static Optional<UserBuyer> GetUserIdByValue(int userID) {
        return buyers.values().stream().filter(user -> user.userID == userID).findFirst();
    }

    public static void Save() {
        FileIO.WriteBuyers();
    }
    
    @Override
    public String toString() {
        int vip = (int)moneySpent / 100;
        
        return "BUYER: " + super.toString() + "\n"
                + "You spent: " + moneySpent + "\n"
                + "Congratulations! Your VIP level is: " + vip + "!";
        
    }
}
