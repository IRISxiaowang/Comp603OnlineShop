/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package newonlineshop;

/**
 * Main entry class. Creates the Model, Controller and Views.
 * @author xiaowang
 */
public class NewOnlineShop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ShopModel model = new ShopModel();
        ShopView view = new ShopView();
        ShopController controller = new ShopController(model, view);
    }
    
}
