/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * Main controller class for the application. 
 * @author xiaowang
 */
public class ShopController implements ActionListener {
    ShopModel model;
    ShopView view;
    MainBuyMenuView buyMenuView;
    MainSellMenuView sellMenuView;
    AddProductView addProductView;
    RechargeView rechargeView;
    PurchaseHistoryView buyHistoryView;
    SellHistoryView sellHistoryView;

    public ShopController(ShopModel model, ShopView view) {
        this.model = model;
        this.view = view;
        view.addActionListener(this);
    }
    
    /// Handles all the actions triggered by the Views. Action commands are used to 
    /// distinguish what action should be triggered.
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Log in":
                // Checks inputs and attempt to login.
                String username = view.unInput.getText();
                String password = view.pwInput.getText();
                if(username.contains("'")){
                    view.updateMessage("Username cannot contain \" ' \".");
                }else if(password.contains("'")){
                    view.updateMessage("Password cannot contain \" ' \".");
                }else{ 
                    model.login(username, password);
                }
                if (model.getCurrentUser() == null) {
                    view.updateMessage("          Login failed.    Incorrect Username or password.   Please try again.");
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
                                view.baInput.getText(),
                                0
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
                                view.baInput.getText(),
                                0
                        );
                    }
                    // Check if data is valid
                    if (isUserDataValid(user)) {
                        registerNewUser(user);
                    }
                }
                break;
            case "Cancel":
                // Cancles out of the registration menu. Return to login menu.
                view.showLoginMenu();
                view.updateMessage(" ");
                break;
            case "Log out":
                // Return to the login page.
                if (model.getCurrentUser().role == Role.SELLER) {
                    sellMenuView.setVisible(false);
                }
                if (model.getCurrentUser().role == Role.BUYER) {
                    buyMenuView.setVisible(false);
                }
                if(buyHistoryView != null && buyHistoryView.isActive()){
                    buyHistoryView.setVisible(false);
                }
                if(sellHistoryView != null && sellHistoryView.isActive()){
                    sellHistoryView.setVisible(false);
                }
                model.logout();
                view.setVisible(true);
                view.showLoginMenu();
                view.updateMessage(" ");
                break;
            case "Purchase History":
                // Shows the purchase history view.
                if (model.getCurrentUser().role == Role.BUYER) {
                    buyMenuView.setVisible(false);
                }
                buyHistoryView = new PurchaseHistoryView();
                buyHistoryView.addActionListener(this);
                buyHistoryView.getUserName().setText("Hello < " + model.getCurrentUser().username + " >");
                buyHistoryView.setVisible(true);
                break;
            case "Sell History":
                // Shows the sales history view.
                if (model.getCurrentUser().role == Role.SELLER) {
                    sellMenuView.setVisible(false);
                }
                sellHistoryView = new SellHistoryView();
                sellHistoryView.addActionListener(this);
                sellHistoryView.getUserName().setText("Hello < " + model.getCurrentUser().username + " >");
                sellHistoryView.setVisible(true);
                break;
            case "Recharge Balance":
                // Shows the Recharnge balance view.
                if (model.getCurrentUser().role == Role.BUYER) {
                    buyMenuView.setVisible(false);
                }
                rechargeView = new RechargeView();
                rechargeView.addActionListener(this);
                rechargeView.setVisible(true);
                break;
            case "Buy":
                // Buys the current selected item, transfer the funds to seller and adds a trasaction.
                int row = buyMenuView.productsTable.getSelectedRow();
                if(row != -1){
                    String selectedProductName = (String) buyMenuView.productsTable.getValueAt(row, 0);
                    buy(selectedProductName);
                }else{
                    buyMenuView.updateMessage("Before click buy botton, please choose a product.");
                }
                break;
            case "Sell":
                // Go to the Sell product view.
                sellMenuView.setVisible(false);
                addProductView = new AddProductView();
                addProductView.addActionListener(this);
                addProductView.setVisible(true);
                break;
            case "Back to sell menu":
                // return to the main sell view from Sell product view.
                sellMenuView.refreshProductsTable();
                addProductView.setVisible(false);
                sellMenuView.setVisible(true);
                break;
            case "Back to sell":
                // return to the main sell view from History.
                sellMenuView.refreshProductsTable();
                sellHistoryView.setVisible(false);
                sellMenuView.setVisible(true);
                break;
            case "Back to buy menu":
                // return to the main buy menu from recharge view
                buyMenuView.refreshProductsTable();
                rechargeView.setVisible(false);
                buyMenuView.setVisible(true);
                break;
            case "Back to buy":
                // return to the main buy menu from buy history view.
                buyMenuView.refreshProductsTable();
                buyHistoryView.setVisible(false);
                buyMenuView.setVisible(true);
                break;
            case "Add product":
                // Add a new product to sell.
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
                // Recharge some money to the current user.
                if (this.isRechargeDataValid()) {
                    User user = model.getCurrentUser();
                    double amount = Double.parseDouble(rechargeView.amountInput.getText());
                    user.balance += amount;
                    model.updateUserBalance(model.getCurrentUser().userID, user.balance);
                    buyMenuView.updateBalance(user.balance);

                    rechargeView.setVisible(false);
                    buyMenuView.setVisible(true);
                }
                break;

            case "Search":
                /// Perform search for the current product table.
                if (model.getCurrentUser().role == Role.SELLER) {
                    if(sellMenuView.isActive()){
                        if(isSearchValid()){
                            sellMenuView.updateMessage("");
                            ArrayList<String[]> search = model.searchProduct(sellMenuView.searchInput.getText());
                            sellMenuView.refreshSearchTable(search);
                        }
                    }                    
                }
                if (model.getCurrentUser().role == Role.BUYER) {
                    if(buyMenuView.isActive()){
                        if(isSearchValid()){
                            buyMenuView.updateMessage("");
                            ArrayList<String[]> search = model.searchProduct(buyMenuView.searchInput.getText());
                            buyMenuView.refreshSearchTable(search);
                        }
                    }
                }
                break;
            case "Quit":
                System.exit(0);

        }

    }
    
    /// Register a new user.
    public void registerNewUser(User user) {
        if (model.hasUser(user.username)) {
            view.updateMessage("Registeration failed. User aready exists");
        } else {
            model.registerNewUser(user);
            view.showLoginMenu();
        }
    }
    
    /// Buys an product. Transfer the payment and add transaction.
    private void buy(String productName) {
        Product soldProduct = model.getProduct(productName);
        UserSeller owner = model.getProductOwner(productName);
       
        if(model.getCurrentUser().userID == owner.userID){
            buyMenuView.updateMessage("Please choose other products, you cannot buy from yourself.");
        }
        
        if(model.getCurrentUser().balance < soldProduct.price){
            buyMenuView.updateMessage("Please recharge your account first, money is not enough.");
        }else{
            // Add a new transaction
            LocalDate date = LocalDate.now();
            Transaction newTransaction = new Transaction(soldProduct.productID, model.getCurrentUser().userID, model.getCurrentUser().bankAccount, owner.userID, owner.bankAccount, date);
            model.addTransaction(newTransaction);
            
            // Update balances for buyer and seller
            double newBuyerBalance = model.getCurrentUser().balance - soldProduct.price;
            model.updateUserBalance(model.getCurrentUser().userID, newBuyerBalance);
            model.getCurrentUser().balance = newBuyerBalance;
            buyMenuView.updateBalance(newBuyerBalance);
            double newSellerBalance = owner.balance + soldProduct.price;
            model.updateUserBalance(owner.userID, newSellerBalance);
            
            // Refresh View
            buyMenuView.refreshProductsTable();
        }
    }


    /// Checks if the recharge input is valid.
    public boolean isRechargeDataValid() {
        if (rechargeView.amountInput.getText().trim().length() == 0) {
            rechargeView.updateMessage("Amount cannot be empty.");
            return false;
        }

        if (rechargeView.amountInput.getText().trim().contains("'")) {
            rechargeView.updateMessage("Amount cannot contain \" ' \".");
            return false;
        }
        
        if (rechargeView.expiryInput.getText().trim().length() == 0) {
            rechargeView.updateMessage("Expiry cannot be empty.");
            return false;
        }

        if (rechargeView.expiryInput.getText().trim().contains("'")) {
            rechargeView.updateMessage("Expiry cannot contain \" ' \".");
            return false;
        }
        
        if (rechargeView.CVVInput.getText().trim().length() == 0) {
            rechargeView.updateMessage("CVV cannot be empty.");
            return false;
        }

        if (rechargeView.CVVInput.getText().trim().contains("'")) {
            rechargeView.updateMessage("CVV cannot contain \" ' \".");
            return false;
        }
        
        if (rechargeView.cardNumberInput.getText().trim().length() == 0) {
            rechargeView.updateMessage("Card Number cannot be empty.");
            return false;
        }

        if (rechargeView.cardNumberInput.getText().trim().contains("'")) {
            rechargeView.updateMessage("Card Number cannot contain \" ' \".");
            return false;
        }
        
        if (rechargeView.holderNameInput.getText().trim().length() == 0) {
            rechargeView.updateMessage("Holder Name cannot be empty.");
            return false;
        }
        
        if (rechargeView.holderNameInput.getText().trim().contains("'")) {
            rechargeView.updateMessage("Holder Name cannot contain \" ' \".");
            return false;
        }
        try {
            if (Double.parseDouble(rechargeView.amountInput.getText()) <= 0) {
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
    ///Checks if a string input is valid.
    public boolean isSearchValid(){
        if (sellMenuView != null && sellMenuView.searchInput.getText().trim().contains("'")) {
            sellMenuView.updateMessage("Please try again.  Search cannot contain \" ' \".");
            return false;
        }
        if (buyMenuView != null && buyMenuView.searchInput.getText().trim().contains("'")) {
            buyMenuView.updateMessage("Please try again.  Search cannot contain \" ' \".");
            return false;
        }
        return true;
    }
    /// Checks if a product data is valid.
    public boolean isProductDataValid(Product product) {
        if (product.productName.trim().length() == 0) {
            addProductView.updateMessage("Product name cannot be empty.");
            return false;
        }
        if (product.productName.trim().contains("'")) {
            addProductView.updateMessage("Product name cannot contain \" ' \".");
            return false;
        }

        if (product.description.trim().length() == 0) {
            addProductView.updateMessage("Product description cannot be empty.");
            return false;
        }
        if (product.description.trim().contains("'")) {
            addProductView.updateMessage("Product description cannot contain \" ' \".");
            return false;
        }

        if (product.price <= 0) {
            addProductView.updateMessage("Product price cannot be nagative.");
            return false;
        }
        return true;
    }
    
    /// Checks if user data is valid
    public boolean isUserDataValid(User user) {
        // Check username
        // Check password
        if (user.username.trim().length() == 0) {
            view.updateMessage("Username cannot be empty.");
            return false;
        }
        if (user.username.trim().contains("'")) {
            view.updateMessage("Username cannot contain \" ' \".");
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
        
        if (user.password.trim().contains("'")) {
            view.updateMessage("Password cannot contain \" ' \".");
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
        
        if (user.name.trim().contains("'")) {
            view.updateMessage("Name cannot contain \" ' \".");
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
        
        if (user.address.trim().contains("'")) {
            view.updateMessage("Address cannot contain \" ' \".");
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
        
        if (user.phone.trim().contains("'")) {
            view.updateMessage("Phone cannot contain \" ' \".");
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
        
        if (user.email.trim().contains("'")) {
            view.updateMessage("Email cannot contain \" ' \".");
            return false;
        }
        if (user.bankAccount.trim().length() == 0) {
            view.updateMessage("Bank account cannot be empty.");
            return false;
        }
        if (user.bankAccount.trim().contains("'")) {
            view.updateMessage("Bank account cannot contain \" ' \".");
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
