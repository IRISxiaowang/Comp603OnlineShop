/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newonlineshop;

/**
 *
 * @author xiaowang
 */
public enum ProgramStage {
    MAINMENU,
    LOGIN, 
    REGISTER, 
    SHOPMENU, 
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
            case SHOPMENU:
                return "Shop Menu";
            case EXIT:
                return "Exit";
        }
        return "";
    }
    
    
}
