/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author xiaowang
 */
public class ShopView extends JFrame implements Observer{
    ProgramStage currentStage;
    
    private int ROW_HEIGHT = 100;
    
    private JPanel userPanel = new JPanel();
    private JLabel uName = new JLabel("Username: ");
    private JLabel pWord = new JLabel("Password: ");
    public JLabel info = new JLabel(" ");
    public JTextField unInput = new JTextField(20);
    public JTextField pwInput = new JTextField(20);
    
    private JButton registerButton = new JButton("Register");
    private JButton loginButton = new JButton("Log in");
    private JButton quitButton = new JButton("Quit");
    
    private JPanel registerPanel = new JPanel();
    private JLabel rName = new JLabel("    Name: ");
    public JTextField rnInput = new JTextField(20);
    private JLabel bankAccount = new JLabel("Bank Account: ");
    public JTextField baInput = new JTextField(20);
    //private JLabel registerInfo = new JLabel(" ");
    private final String[] s = {"Buyer", "Seller"};
    
    private JLabel addressLabel = new JLabel(" Address: ");
    public JTextField addressInput = new JTextField(20);
    
    private JLabel phoneLabel = new JLabel("   Phone: ");
    public JTextField phoneInput = new JTextField(20);
    
    private JLabel emailLabel = new JLabel("   Email: ");
    public JTextField emailInput = new JTextField(20);
    
    public JComboBox role = new JComboBox(s);
    
    public ShopView(){
        currentStage = ProgramStage.LOGIN;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(680, 260);
        this.setResizable(false);
        showLoginMenu();
        this.setVisible(true);
    }
    
    public void showLoginMenu(){
        registerPanel.setVisible(false);
        currentStage = ProgramStage.LOGIN;
        unInput.setText("1234");
        pwInput.setText("1234");
        
        userPanel.add(uName);
        userPanel.add(unInput);
        
        userPanel.add(pWord);
        userPanel.add(pwInput);
        userPanel.add(loginButton);
        userPanel.add(registerButton);
        quitButton.setText("Quit");
        userPanel.add(quitButton);
        userPanel.add(info);
        
        this.add(userPanel);
        userPanel.setVisible(true);
    }
    
    public void showRegisterMenu(){
        userPanel.setVisible(false);
        currentStage = ProgramStage.REGISTER;
        unInput.setText("");
        rnInput.setText("");
        pwInput.setText("");
        baInput.setText("");
        addressInput.setText("");
        phoneInput.setText("");
        emailInput.setText("");
        
        registerPanel.add(uName);
        registerPanel.add(unInput);
        registerPanel.add(pWord);
        registerPanel.add(pwInput);
        
        registerPanel.add(rName);
        registerPanel.add(rnInput);
        registerPanel.add(bankAccount);
        registerPanel.add(baInput);
       
        
        registerPanel.add(addressLabel);
        registerPanel.add(addressInput);
        registerPanel.add(phoneLabel);
        registerPanel.add(phoneInput);
        registerPanel.add(emailLabel);
        registerPanel.add(emailInput);
         
        registerPanel.add(role);
        registerPanel.add(registerButton);
        quitButton.setText("Cancel");
        registerPanel.add(quitButton);
        registerPanel.add(info);
        
        this.add(registerPanel);
        registerPanel.setVisible(true);
    }
    
    public void showMainMenu(){
        userPanel.setVisible(true);
        currentStage = ProgramStage.MAINMENU;
       // if(buyer)show products, seller add products
    }
    public void addActionListener(ActionListener listener){//****controller switch to different action 
        this.registerButton.addActionListener(listener);
        this.loginButton.addActionListener(listener);
        this.quitButton.addActionListener(listener);
    }
    
    @Override
    public void update(Observable o, Object arg) {
       
    }
    
    
    public void updateMessage(String message) {
        this.info.setText(message);
    }
}
