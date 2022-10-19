/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 *
 * @author xiaowang
 */
public class ShopController implements ActionListener {

    ShopModel model;
    ShopView view;
    MainMenuView menuView;

    public ShopController(ShopModel model, ShopView view){//, MainMenuView menuView) {
        this.model = model;
        this.view = view;
//        this.menuView = menuView;
//        menuView.addActionListener(this);
        view.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Log in":
                String username = view.unInput.getText();
                String password = view.pwInput.getText();
                model.login(username, password);
                if (model.getCurrentUser() == null) {
                    view.updateMessage("Login failed. Incorrect Username or password.");
                } else {
                    view.updateMessage("Login Successful. Welcome " + model.getCurrentUser().name);
                    view.dispose();
                    menuView = new MainMenuView();
                    //view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
                }
                break;
            case "Register":
                // Open registration menu if it is not already open
                if(view.currentStage != ProgramStage.REGISTER ){
                    view.showRegisterMenu();
                } else {
                    // Otherewise register a new user.
                    int role = view.role.getSelectedIndex();
                    User user = null;
                    if (role == 0) {
                        user = new UserBuyer(
                            0,
                            view.unInput.getText(),
                            view.pwInput.getText(),
                            view.rnInput.getText(),
                            view.addressInput.getText(),
                            view.phoneInput.getText(),
                            view.emailInput.getText(),
                            view.baInput.getText()
                        );

                    } else if (role == 1) {
                        user = new UserSeller(
                            0,
                            view.unInput.getText(),
                            view.pwInput.getText(),
                            view.rnInput.getText(),
                            view.addressInput.getText(),
                            view.phoneInput.getText(),
                            view.emailInput.getText(),
                            view.baInput.getText()
                        );
                    }
                    // Check if data is valid
                    if(isUserDataValid(user)){
                        registerNewUser(user);
                    }
                }
                break;
            case "Cancel":
                view.showLoginMenu();
                break;
            case "Quit":
                System.exit(0);
                
        }

    }

    public void registerNewUser(User user) {
        if (model.hasUser(user.username)) {
            view.updateMessage("Registeration failed. User aready exists");
        } else {
            model.registerNewUser(user);
            view.showLoginMenu();
        }
    }
    
    public boolean isUserDataValid(User user){
        // Check username
        // Check password
        if (user.username.trim().length() == 0) {
            view.updateMessage("Username cannot be empty.");
            return false;
        }
        // No white space
        if (user.username.contains(" ")) {
            view.updateMessage("The username cannot contain whitespace, try again.\n");
            return false;
        }
        // Too short
        if (user.username.trim().length() < 3) {
            view.updateMessage("Username too short, at least 3 characters, try again.\n");
            return false;
        }
        // Too long
        if (user.username.length() > 16) {
            view.updateMessage("Username too long, max of 16 characters, try again.\n");
            return false;
        }
        if (user.password.trim().length() == 0) {
            view.updateMessage("Password cannot be empty.");
            return false;
        }
        // No white space
        if (user.password.contains(" ")) {
            view.updateMessage("The password cannot contain whitespace, try again.\n");
            return false;
        }
        // Too short
        if (user.password.trim().length() < 4) {
            view.updateMessage("Password too short, at least 4 characters, try again.\n");
           return false;
        }
        // Too long
        if (user.password.length() > 16) {
            view.updateMessage("Password too long, max of 16 characters, try again.\n");
           return false;
        }
        if (user.name.trim().length() == 0) {
            view.updateMessage("Name cannot be empty.");
            return false;
        }
        // Too long
        if (user.name.length() > 128) {
            view.updateMessage("Name too long, max of 128 characters, try again.\n");
            return false;
        }
        if (user.address.trim().length() == 0) {
            view.updateMessage("Address cannot be empty.");
            return false;
        }
        // Too long
        if (user.address.length() > 512) {
            view.updateMessage("Address too long, max of 512 characters, try again.\n");
            return false;
        }
        if (user.phone.trim().length() == 0) {
            view.updateMessage("Phone cannot be empty.");
            return false;
        }
        // Too long
        if (user.phone.length() > 12 + 4) {
            view.updateMessage("Phone too long, max of 16 digits, try again.\n");
            return false;
        }
        // Too short
        if (user.phone.length() < 3) {
            view.updateMessage("Phone too short, at least 3 digits, try again.\n");
            return false;
        }
        if (user.email.trim().length() == 0) {
            view.updateMessage("Email cannot be empty.");
            return false;
        }
        if (user.bankAccount.trim().length() == 0) {
            view.updateMessage("Bank account cannot be empty.");
            return false;
        }
        // Too long
        if (user.bankAccount.length() > 28) {
            view.updateMessage("Account too long, max of 28 digits, try again.\n");
            return false;
        }
        // Too short
        if (user.bankAccount.length() < 3) {
            view.updateMessage("Account too short, at least 3 digits, try again.\n");
            return false;
        }
        return true;
    }
}
