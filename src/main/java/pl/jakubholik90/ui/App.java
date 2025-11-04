package pl.jakubholik90.ui;

import pl.jakubholik90.menus.MainMenu;
import pl.jakubholik90.menus.Menu;
import pl.jakubholik90.menus.NewModifyMenu;
import pl.jakubholik90.menus.NewModifyNewMenu;

import java.util.ArrayList;

public class App {
    MainMenu mainMenu;
    NewModifyMenu newModifyMenu;
    NewModifyNewMenu newModifyNewMenu;
    UI actualUI;

    public App(UI actualUI) {
        this.actualUI = actualUI;
        this.initializeMenus();
    }


    public void runApp() {
        actualUI.displayMessage("Welcome to the Neural Network Application!");
        this.mainMenu.runMenu();
    }

    private void initializeMenus() {
        // assign UI to menus
        this.mainMenu = new MainMenu(actualUI);
        this.newModifyMenu = new NewModifyMenu(actualUI);

        // inject dependencies between menus
        this.mainMenu.setNewModifyMenu(this.newModifyMenu);
        this.newModifyMenu.setMainMenu(this.mainMenu);
        this.newModifyNewMenu.setNewModifyMenu(this.newModifyMenu);
    }


}
