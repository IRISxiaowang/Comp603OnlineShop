/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * View for recharging balance in an account.
 * @author xiaowang
 */
public class RechargeView extends JFrame {
    private JPanel rechargePanel = new JPanel();
    private JLabel cardUserName = new JLabel("Holder Name: ");
    private JLabel amount = new JLabel("Amount: ");
    private JLabel bankAccount = new JLabel(" Card Number: ");
    private JLabel expiry = new JLabel("Expiry: ");
    private JLabel CVV = new JLabel("CVV : ");
    private JLabel info = new JLabel(" ");
    
    public JTextField holderNameInput = new JTextField(20);
    public JTextField amountInput = new JTextField(20);
    public JTextField cardNumberInput = new JTextField(20);
    public JTextField expiryInput = new JTextField(20);
    public JTextField CVVInput = new JTextField(20);
    
    private JButton toSellerButton = new JButton("Back to buy menu");
    private JButton rechargeButton = new JButton("Recharge");
    
    public RechargeView(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(680, 200);
        this.setResizable(false);
        //this.model = model;
        showRechargeMenu();
        this.setVisible(true);
    }
    
    /// Sets up the GUI
    public void showRechargeMenu(){
        holderNameInput.setText("");
        amountInput.setText("");
        cardNumberInput.setText("");
        expiryInput.setText("");
        CVVInput.setText("");
        
        rechargePanel.add(cardUserName);
        rechargePanel.add(holderNameInput);
        rechargePanel.add(amount);
        rechargePanel.add(amountInput);
        rechargePanel.add(bankAccount);
        rechargePanel.add(cardNumberInput);
        rechargePanel.add(expiry);
        rechargePanel.add(expiryInput);
        rechargePanel.add(CVV);
        rechargePanel.add(CVVInput);
        
        rechargePanel.add(rechargeButton);
        rechargePanel.add(toSellerButton);
        
        rechargePanel.add(info);
        
        this.add(rechargePanel);
        rechargePanel.setVisible(true);
    }
    
    /// Add action listener for all elements
    public void addActionListener(ActionListener listener){
        this.rechargeButton.addActionListener(listener);
        this.toSellerButton.addActionListener(listener);
    }
    
    /// Update user messages
    public void updateMessage(String message) {
        this.info.setText(message);
    }
}


