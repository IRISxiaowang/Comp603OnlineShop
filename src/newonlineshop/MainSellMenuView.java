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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author xiaowang
 */
public class MainSellMenuView extends JFrame implements Observer{
    ProgramStage currentStage;
    
    private JPanel sellPanel = new JPanel();
    private JLabel userName = new JLabel();
    private JLabel currentBalance = new JLabel();
    private JButton search = new JButton("Search");
    private JLabel info = new JLabel(" ");
    
    public JTextField searchInput = new JTextField(20);
    
    private JTable productsTable;
    
    private JButton logoutButton = new JButton("Log out");
    private JButton sellButton = new JButton("Sell");
    private JButton sellHistoryButton = new JButton("Sell History");
    private JButton quitButton = new JButton("Quit");
    
    public MainSellMenuView(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(680, 550);
        this.setResizable(false);
        showMainSellMenu();
        this.setVisible(true);
    }
    public void showMainSellMenu(){
        currentStage = ProgramStage.SHOPMENUSELL;
        sellPanel.add(getUserName());
        
        sellPanel.add(search);
        sellPanel.add(searchInput);
        
        sellPanel.add(logoutButton);
        sellPanel.add(sellButton);
        sellPanel.add(getCurrentBalance());
        
        sellPanel.add(sellHistoryButton);
        sellPanel.add(quitButton);
        
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
        sellPanel.add(new JScrollPane(productsTable));
        
        sellPanel.add(info);
        
        this.add(sellPanel);
        sellPanel.setVisible(true);
        
    }
    
    public void refreshProductsTable(){
        String[][] productContent = ShopModel.getOnSaleProduct().toArray(new String[0][0]);
        DefaultTableModel tableModel = (DefaultTableModel)productsTable.getModel();
        String[] column = {"productName", "price", "description"};
        tableModel.setDataVector(productContent, column);
    }
    public void refreshSearchTable(ArrayList<String[]> search){
        String[][] productContent = search.toArray(new String[0][0]);
        DefaultTableModel tableModel = (DefaultTableModel)productsTable.getModel();
        String[] column = {"productName", "price", "description"};
        tableModel.setDataVector(productContent, column);
    }
    public void addActionListener(ActionListener listener){//****controller switch to different action 
        this.search.addActionListener(listener);
        this.sellHistoryButton.addActionListener(listener);
        this.quitButton.addActionListener(listener);
        this.logoutButton.addActionListener(listener);
        this.sellButton.addActionListener(listener);
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


