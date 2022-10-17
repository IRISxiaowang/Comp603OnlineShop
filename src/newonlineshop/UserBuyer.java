/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;


/**
 *
 * @author xiaowang
 */
public class UserBuyer extends User {
    private double moneySpent;
    
    public UserBuyer(int userID, String username, String password, String name, String address, String phone, String email, String bankAccount) {
        super(userID, username, password, name, address, phone, email, bankAccount, Role.BUYER);
        moneySpent = 0;
    }
    
    public double getMoneySpent(){
        return moneySpent;
    }
    
    public void spent(double amount){
        moneySpent += amount;
    }
    
    @Override
    public String toString() {
        int vip = (int)moneySpent / 100;
        
        return "BUYER: " + super.toString() + "\n"
                + "You spent: " + moneySpent + "\n"
                + "Congratulations! Your VIP level is: " + vip + "!";
        
    }
}
