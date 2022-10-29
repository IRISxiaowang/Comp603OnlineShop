/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

/**
 * This class represents a seller. Inherits User class.
 * @author xiaowang
 */
public class UserSeller extends User {
    public int starRating;
    
    public UserSeller(int userID, String username, String password, String name, String address, String phone, String email, String bankAccount, double balance ) {
        super(userID, username, password, name, address, phone, email, bankAccount, Role.SELLER, balance);
        starRating = 0;
    }
    
    @Override
    public String toString() {
        String rating;
        if(starRating < 2) {
            rating = "Your Rating is very low.";
        } else if(starRating < 4) {
            rating = "Your Rating is high.";
        } else {
            rating = "Congratulations! Your Rating MAX.";
        }
        return "SELLER: " + super.toString() + "\n"
                + "Seller Rating: " + starRating + "\n"
                + rating;
    }
}


