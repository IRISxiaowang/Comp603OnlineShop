/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author xiaowang
 */
public class ShopController implements ActionListener {

    ShopModel model;
    ShopView view;

    public ShopController(ShopModel model, ShopView view) {
        this.model = model;
        this.view = view;
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
                if (model.currentUser == null) {
                    view.updateMessage("Login failed. Incorrect Username or password.");
                } else {
                    view.updateMessage("Login Successful. Welcome " + model.currentUser.name);
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
                    registerNewUser(user);
                }
                break;
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

}
