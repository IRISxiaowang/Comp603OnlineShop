/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

/**
 * Class contains which stage the program is currently in.
 * @author xiaowang
 */
public enum ProgramStage {
    MAINMENU,
    LOGIN, 
    REGISTER, 
    SHOPMENUBUY, 
    SHOPMENUSELL,
    EXIT;
    
    @Override
    public String toString(){
        switch(this) {
            case MAINMENU:
                return "Main Menu";
            case LOGIN:
                return "Login";
            case REGISTER:
                return "Register";
            case SHOPMENUBUY:
                return "Shop Buy Menu";
            case SHOPMENUSELL:
                return "Shop Sell Menu";
            case EXIT:
                return "Exit";
        }
        return "";
    }
    
    
}
