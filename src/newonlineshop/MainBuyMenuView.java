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
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author xiaowang
 */
public class MainBuyMenuView extends JFrame implements Observer{
    ProgramStage currentStage;
   
    
    private JPanel buyPanel = new JPanel();
    private JLabel userName = new JLabel();
    private JLabel currentBalance = new JLabel();
    private JButton search = new JButton("Search");
    private JLabel info = new JLabel(" ");
    
    public JTextField searchInput = new JTextField(20);
    
    public JTable productsTable;
    
    private JButton logoutButton = new JButton("Log out");
    private JButton buyButton = new JButton("Buy");
    private JButton purchaseHistoryButton = new JButton("Purchase History");
    private JButton rechargeBalanceButton = new JButton("Recharge Balance");
    private JButton quitButton = new JButton("Quit");
    
    public MainBuyMenuView(){
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(680, 550);
        this.setResizable(false);
        
        showMainBuyMenu();
        this.setVisible(true);
    }
    public void showMainBuyMenu(){
        currentStage = ProgramStage.SHOPMENUBUY;
        buyPanel.add(getUserName());
        
        buyPanel.add(search);
        buyPanel.add(searchInput);
        
        buyPanel.add(logoutButton);
        buyPanel.add(buyButton);
        buyPanel.add(getCurrentBalance());
        buyPanel.add(rechargeBalanceButton);
        buyPanel.add(purchaseHistoryButton);
        buyPanel.add(quitButton);
        
        String[][] productContent = ShopModel.getOnSaleProduct().toArray(new String[0][0]);
        String[] column = {"productName", "price", "description"};
        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        tableModel.setDataVector(productContent, column);
        productsTable = new JTable(tableModel);
        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        buyPanel.add(new JScrollPane(productsTable));
        
        buyPanel.add(info);
        
        this.add(buyPanel);
        buyPanel.setVisible(true);
        //this.setVisible(true);
    }
    
   
    public void addActionListener(ActionListener listener){//****controller switch to different action 
        this.rechargeBalanceButton.addActionListener(listener);
        this.purchaseHistoryButton.addActionListener(listener);
        this.quitButton.addActionListener(listener);
        this.logoutButton.addActionListener(listener);
        this.buyButton.addActionListener(listener);
        this.search.addActionListener(listener);
    }
    
    public void refreshSearchTable(ArrayList<String[]> search){
        String[][] productContent = search.toArray(new String[0][0]);
        DefaultTableModel tableModel = (DefaultTableModel)productsTable.getModel();
        String[] column = {"productName", "price", "description"};
        tableModel.setDataVector(productContent, column);
    }
    
    public void refreshProductsTable(){
        String[][] productContent = ShopModel.getOnSaleProduct().toArray(new String[0][0]);
        DefaultTableModel tableModel = (DefaultTableModel)productsTable.getModel();
        String[] column = {"productName", "price", "description"};
        tableModel.setDataVector(productContent, column);
    }
    
    public void updateBalance(double balance) {
        currentBalance.setText("Current balance < "+String.valueOf(balance)+" >");
    }
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void updateMessage(String message) {
        this.info.setText(message);
    }
    
    /**
     * @return the userName
     */
    public JLabel getUserName() {
        return userName;
    }

    /**
     * @return the currentBalance
     */
    public JLabel getCurrentBalance() {
        return currentBalance;
    }
}
