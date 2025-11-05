package pl.jakubholik90.ui;

import pl.jakubholik90.menus.MainMenu;
import pl.jakubholik90.menus.NewModifyMenu;
import pl.jakubholik90.menus.NewModifyNewMenu;

public class App {
    private MainMenu mainMenu;
    private NewModifyMenu newModifyMenu;
    private NewModifyNewMenu newModifyNewMenu;
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
        // create new menus and assign UI to menus
        this.mainMenu = new MainMenu(actualUI);
        this.newModifyMenu = new NewModifyMenu(actualUI);
        this.newModifyNewMenu = new NewModifyNewMenu(actualUI);

        // inject dependencies between menus
        //"main" menu connections
        this.mainMenu.setNewModifyMenu(this.newModifyMenu); // from MainMenu to NewModifyMenu

        // "new/modify" menu connections
        this.newModifyMenu.setMainMenu(this.mainMenu); // from NewModifyMenu to MainMenu
        this.newModifyMenu.setNewModifyNewMenu(this.newModifyNewMenu); // from NewModifyMenu to NewModifyNewMenu

        // "new/modify-new" menu connections
        this.newModifyNewMenu.setNewModifyMenu(this.newModifyMenu); // from NewModifyNewMenu to NewModifyMen
    }


}
