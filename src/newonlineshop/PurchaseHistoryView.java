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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * View for displaying purchase history
 * @author xiaowang
 */
public class PurchaseHistoryView extends JFrame {
    private JPanel buyHistoryPanel = new JPanel();
    private JLabel userName = new JLabel();
    
    public JTable transactionTable;
    
    private JButton logoutButton = new JButton("Log out");
    private JButton bactToBuyButton = new JButton("Back to buy");
    private JButton quitButton = new JButton("Quit");
    
    public PurchaseHistoryView(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(680, 550);
        this.setResizable(false);
        showBuyHistoryMenu();
        this.setVisible(true);
    }
    
    /// Sets up the GUI
    public void showBuyHistoryMenu(){
        
        buyHistoryPanel.add(getUserName());
      
        buyHistoryPanel.add(bactToBuyButton);
        buyHistoryPanel.add(logoutButton);
        buyHistoryPanel.add(quitButton);
        
        String[][] productContent = ShopModel.getBuyerHistoryInfo().toArray(new String[0][0]);
        String[] column = {"productName", "description","price","sellerName","date"};
        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        tableModel.setDataVector(productContent, column);
        transactionTable = new JTable(tableModel);
        buyHistoryPanel.add(new JScrollPane(transactionTable));
        
        this.add(buyHistoryPanel);
        buyHistoryPanel.setVisible(true);
        //this.setVisible(true);
    }
    
   
    public void addActionListener(ActionListener listener){//****controller switch to different action 
        this.quitButton.addActionListener(listener);
        this.logoutButton.addActionListener(listener);
        this.bactToBuyButton.addActionListener(listener);
    }
    
    /// Gets the purchase history data and fill the data table.
    public void refreshProductsTable(){
        String[][] productContent = ShopModel.getBuyerHistoryInfo().toArray(new String[0][0]);
        DefaultTableModel tableModel = (DefaultTableModel)transactionTable.getModel();
        String[] column = {"productName", "description","price","sellerName","date"};
        tableModel.setDataVector(productContent, column);
    }
    
    /**
     * @return the userName
     */
    public JLabel getUserName() {
        return userName;
    }
}

