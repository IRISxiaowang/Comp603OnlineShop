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
 * This class contains the view for adding a new product.
 * @author xiaowang
 */
public class AddProductView extends JFrame  {
    private JPanel addPanel = new JPanel();
    private JLabel productName = new JLabel("Product Name: ");
    private JLabel price = new JLabel("Price: ");
    private JLabel description = new JLabel("Description: ");
    private JLabel info = new JLabel(" ");
    
    public JTextField productNameInput = new JTextField(20);
    public JTextField priceInput = new JTextField(20);
    public JTextField descriptionInput = new JTextField(20);
    
    private JButton toSellerButton = new JButton("Back to sell menu");
    private JButton addProductButton = new JButton("Add product");
    
    public AddProductView(){
        // Display the current view.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(680, 200);
        this.setResizable(false);
        //this.model = model;
        showAddProductMenu();
        this.setVisible(true);
    }
    
    public void showAddProductMenu(){
        productNameInput.setText("");
        priceInput.setText("");
        descriptionInput.setText("");
        
        addPanel.add(productName);
        addPanel.add(productNameInput);
        addPanel.add(price);
        addPanel.add(priceInput);
        addPanel.add(description);
        addPanel.add(descriptionInput);
        
        addPanel.add(addProductButton);
        addPanel.add(toSellerButton);
        
        addPanel.add(info);
        
        this.add(addPanel);
        addPanel.setVisible(true);
    }
    
    public void addActionListener(ActionListener listener){//****controller switch to different action 
        this.addProductButton.addActionListener(listener);
        this.toSellerButton.addActionListener(listener);
    }
    
    // for displaying error message to the user.
    public void updateMessage(String message) {
        this.info.setText(message);
    }
}
