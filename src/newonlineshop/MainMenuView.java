/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author xiaowang
 */
public class MainMenuView extends JFrame implements Observer{
    ProgramStage currentStage;
    //ShopModel model;
    
    private JPanel buyPanel = new JPanel();
    private JLabel userName = new JLabel();
    private JLabel currentBalance = new JLabel();
    private JLabel search = new JLabel("Search");
    private JLabel info = new JLabel(" ");
    
    public JTextField searchInput = new JTextField(20);
    
    private JTable productsTable;
    
    private JButton logoutButton = new JButton("Log out");
    private JButton buyButton = new JButton("Buy");
    private JButton purchaseHistoryButton = new JButton("Purchase History");
    private JButton rechargeBalanceButton = new JButton("Recharge Balance");
    private JButton quitButton = new JButton("Quit");
    
    public MainMenuView(){
        //currentStage = ProgramStage.MAINMENU;which is mainmenu?
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setResizable(true);
        //this.model = model;
        showMainBuyMenu();
        this.setVisible(true);
    }
    public void showMainBuyMenu(){
        currentStage = ProgramStage.SHOPMENUBUY;
        buyPanel.add(userName);
        buyPanel.add(currentBalance);
        buyPanel.add(search);
        buyPanel.add(searchInput);
        
        buyPanel.add(logoutButton);
        buyPanel.add(buyButton);
        buyPanel.add(rechargeBalanceButton);
        buyPanel.add(purchaseHistoryButton);
        buyPanel.add(quitButton);
        
        String[][] productContent = ShopModel.getProductInfo().toArray(new String[0][0]);
        String[] column = {"productName", "price", "description"};
        productsTable = new JTable(productContent, column);
        //productsTable.setBounds(10, 10, 100, 600);
        buyPanel.add(new JScrollPane(productsTable));
        
        buyPanel.add(info);
        
        this.add(buyPanel);
        buyPanel.setVisible(true);
        
    }
    
    public void addActionListener(ActionListener listener){//****controller switch to different action 
        this.rechargeBalanceButton.addActionListener(listener);
        this.purchaseHistoryButton.addActionListener(listener);
        this.quitButton.addActionListener(listener);
        this.logoutButton.addActionListener(listener);
        this.buyButton.addActionListener(listener);
    }
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void updateMessage(String message) {
        this.info.setText(message);
    }
//    public static void main(String[] args) {
//        ShopModel model = new ShopModel();
//        MainMenuView v = new MainMenuView();
//    }
}
