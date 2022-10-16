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
public class ShopController implements ActionListener  {

    ShopModel model;
    ShopView view;
    public ShopController(ShopModel model, ShopView view){
        this.model = model;
        this.view = view;
        this.view.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("register")){
            // Handles registration of new users
            String username = this.view.unInput.getText();
            String password = this.view.pwInput.getText();
            String name = this.view.rnInput.getText();
            this.model.checkName(username, password,name);
        }
        
    }
    
}
