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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author xiaowang
 */
public class ShopView extends JFrame implements Observer{
    private JPanel userPanel = new JPanel();
    private JLabel uName = new JLabel("Username: ");
    private JLabel pWord = new JLabel("Password: ");
    private JLabel rName = new JLabel("Name: ");
    private JLabel info = new JLabel(" ");
    public JTextField unInput = new JTextField(10);
    public JTextField pwInput = new JTextField(10);
    public JTextField rnInput = new JTextField(10);
    private JButton registerButton = new JButton("Register");
    private JButton quitButton = new JButton("Quit");
    
    public ShopView(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 600);
        
        userPanel.add(rName);
        userPanel.add(rnInput);
        userPanel.add(uName);
        userPanel.add(unInput);
       
        userPanel.add(pWord);
        userPanel.add(pwInput);
        userPanel.add(registerButton);
        
        userPanel.add(info);
        
        this.add(userPanel);
        this.setVisible(true);
    }
    
    public void addActionListener(ActionListener listener){//****controller switch to different action 
        this.registerButton.setActionCommand("register");
        this.registerButton.addActionListener(listener);
        this.quitButton.addActionListener(listener);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        Data data =(Data) arg;
        if(!data.login){//****wrong password then rewrite username and password
            this.unInput.setText("");
            this.pwInput.setText("");
            this.info.setText("Password or username is wrong");
        }else{
            this.info.setText("Welcome!");
                    //**add functions
        }
    }
    
}
