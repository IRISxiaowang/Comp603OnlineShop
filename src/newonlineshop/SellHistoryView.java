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
 * GUI for displaying Sales history
 * @author xiaowang
 */
public class SellHistoryView extends JFrame {
    private JPanel sellHistoryPanel = new JPanel();
    private JLabel userName = new JLabel();
    
    public JTable transactionTable;
    
    private JButton logoutButton = new JButton("Log out");
    private JButton bactToBuyButton = new JButton("Back to sell");
    private JButton quitButton = new JButton("Quit");
    
    public SellHistoryView(){
       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(680, 550);
        this.setResizable(false);
        showSellHistoryMenu();
        this.setVisible(true);
    }
    
    /// Sets up the GUI
    public void showSellHistoryMenu(){
        
        sellHistoryPanel.add(getUserName());
      
        sellHistoryPanel.add(bactToBuyButton);
        sellHistoryPanel.add(logoutButton);
        sellHistoryPanel.add(quitButton);
        
        String[][] productContent = ShopModel.getSellerHistoryInfo().toArray(new String[0][0]);
        String[] column = {"productName", "description","price","buyerName","date"};
        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        tableModel.setDataVector(productContent, column);
        transactionTable = new JTable(tableModel);
        sellHistoryPanel.add(new JScrollPane(transactionTable));
        
        this.add(sellHistoryPanel);
        sellHistoryPanel.setVisible(true);
        //this.setVisible(true);
    }
    
    /// Add action listeners for all elements.
    public void addActionListener(ActionListener listener){//****controller switch to different action 
        this.quitButton.addActionListener(listener);
        this.logoutButton.addActionListener(listener);
        this.bactToBuyButton.addActionListener(listener);
    }
    
    /// Gets the products sold and fill the table.
    public void refreshProductsTable(){
        String[][] productContent = ShopModel.getSellerHistoryInfo().toArray(new String[0][0]);
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



