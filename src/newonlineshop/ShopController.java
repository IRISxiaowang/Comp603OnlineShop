/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 *
 * @author xiaowang
 */
public class ShopController implements ActionListener {

    ShopModel model;
    ShopView view;
    MainBuyMenuView buyMenuView;
    MainSellMenuView sellMenuView;
    AddProductView addProductView;
    RechargeView rechargeView;

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
                if (model.getCurrentUser() == null) {
                    view.updateMessage("Login failed. Incorrect Username or password.");
                } else {
                    view.updateMessage("Login Successful. Welcome " + model.getCurrentUser().name);
                    view.setVisible(false);
                    //view.dispose();
                    if (model.getCurrentUser().role == Role.BUYER) {
                        buyMenuView = new MainBuyMenuView();
                        buyMenuView.addActionListener(this);
                        buyMenuView.getCurrentBalance().setText("Current balance < " + model.getCurrentUser().balance + " >");
                        buyMenuView.getUserName().setText("Hello < " + model.getCurrentUser().username + " >");
                    }
                    if (model.getCurrentUser().role == Role.SELLER) {
                        sellMenuView = new MainSellMenuView();
                        sellMenuView.addActionListener(this);
                        sellMenuView.getCurrentBalance().setText("Current balance < " + model.getCurrentUser().balance + " >");
                        sellMenuView.getUserName().setText("Hello < " + model.getCurrentUser().username + " >");
                    }
                }
                break;
            case "Register":
                // Open registration menu if it is not already open
                if (view.currentStage != ProgramStage.REGISTER) {
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
                    if (isUserDataValid(user)) {
                        registerNewUser(user);
                    }
                }
                break;
            case "Cancel":
                view.showLoginMenu();
                view.updateMessage(" ");
                break;
            case "Log out":
                if (model.getCurrentUser().role == Role.SELLER) {
                    sellMenuView.setVisible(false);
                }
                if (model.getCurrentUser().role == Role.BUYER) {
                    buyMenuView.setVisible(false);
                }
                view.setVisible(true);
                view.showLoginMenu();
                view.updateMessage(" ");
                break;
            case "Purchase History":
                //callbuy history
                break;
            case "Sell History":
                //call sell history
                break;
            case "Recharge Balance":
//                if (model.getCurrentUser().role == Role.SELLER) {
//                    sellMenuView.setVisible(false);
//                }
                if (model.getCurrentUser().role == Role.BUYER) {
                    buyMenuView.setVisible(false);
                }
                rechargeView = new RechargeView();
                rechargeView.addActionListener(this);
                rechargeView.setVisible(true);
                break;
            case "Buy":
                // buy add product to buyid and reduce the money from bankaccont
                break;
            case "Sell":
                sellMenuView.setVisible(false);
                addProductView = new AddProductView();
                addProductView.addActionListener(this);
                addProductView.setVisible(true);
                break;
            case "Back to sell menu":
                sellMenuView.refreshProductsTable();
                addProductView.setVisible(false);
                //rechargeView.setVisible(false);
                sellMenuView.setVisible(true);
                break;
            case "Back to buy menu":
                buyMenuView.refreshProductsTable();
                rechargeView.setVisible(false);
                buyMenuView.setVisible(true);
                break;
            case "Add product":
                try {
                Product product = new Product(
                        0,
                        model.getCurrentUser().userID,
                        addProductView.productNameInput.getText(),
                        Double.parseDouble(addProductView.priceInput.getText()),
                        addProductView.descriptionInput.getText()
                );

                if (isProductDataValid(product)) {
                    if (model.hasProduct(product.productName)) {
                        addProductView.updateMessage("Add product failed. Product aready exists.");
                    } else {
                        model.addProduct(product);
                        addProductView.updateMessage("Add product successful.");
                        sellMenuView.refreshProductsTable();
                        addProductView.setVisible(false);
                        sellMenuView.setVisible(true);
                    }

                }

            } catch (Exception error) {
                System.out.println(error);
                addProductView.updateMessage("Add product failed. Price should be a number.");
            }
            break;
            case "Recharge":
                if(this.isRechargeDataValid()){
                    User user =  model.getCurrentUser();
                    double amount = Double.parseDouble(rechargeView.amountInput.getText());
                    user.balance += amount;
                    model.updateUserBalance(model.getCurrentUser().userID, user.balance);
                    buyMenuView.updateBalance(user.balance);
                }
                rechargeView.setVisible(false);
                buyMenuView.setVisible(true);
                break;

            case "Search":
                if (model.getCurrentUser().role == Role.SELLER) {
                    ArrayList<String[]> search = model.searchProduct(sellMenuView.searchInput.getText());
                    sellMenuView.refreshSearchTable(search);
                   
                }
                if (model.getCurrentUser().role == Role.BUYER) {
                    ArrayList<String[]> search = model.searchProduct(buyMenuView.searchInput.getText());
                    buyMenuView.refreshSearchTable(search);
                }
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

//    public void addNewProduct(Product product) {
//        if (model.hasProduct(product.productName)) {
//            addProductView.updateMessage("Add product failed. Product aready exists.");
//        } else {
//            model.addProduct(product);
//            addProductView.updateMessage("Add product successful.");
//        }
//    }
    public boolean isRechargeDataValid() {

        if (rechargeView.amountInput.getText().trim().length() == 0) {
            rechargeView.updateMessage("Amount cannot be empty.");
            return false;
        }

        if (rechargeView.expiryInput.getText().trim().length() == 0) {
            rechargeView.updateMessage("Expiry cannot be empty.");
            return false;
        }

        if (rechargeView.CVVInput.getText().trim().length() == 0) {
            rechargeView.updateMessage("CVV cannot be empty.");
            return false;
        }

        if (rechargeView.cardNumberInput.getText().trim().length() == 0) {
            rechargeView.updateMessage("Card Number cannot be empty.");
            return false;
        }

        if (rechargeView.holderNameInput.getText().trim().length() == 0) {
            rechargeView.updateMessage("Holder Name cannot be empty.");
            return false;
        }
        try {
            if (Double.parseDouble(rechargeView.amountInput.getText()) < 0) {
                rechargeView.updateMessage("Amount cannot be nagative.");
                return false;
            }
        } catch (Exception error) {
            System.out.println(error);
            rechargeView.updateMessage("Amount should be a possitive number.");
            return false;
        }
        return true;
    }

    public boolean isProductDataValid(Product product) {

        if (product.productName.trim().length() == 0) {
            addProductView.updateMessage("Product name cannot be empty.");
            return false;
        }

        if (product.description.trim().length() == 0) {
            addProductView.updateMessage("Product description cannot be empty.");
            return false;
        }

        if (product.price <= 0) {
            addProductView.updateMessage("Product price cannot be nagative.");
            return false;
        }
        return true;
    }

    public boolean isUserDataValid(User user) {
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
