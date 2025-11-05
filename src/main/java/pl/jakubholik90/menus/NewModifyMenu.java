package pl.jakubholik90.menus;

import pl.jakubholik90.ui.UI;

public class NewModifyMenu extends MenuAbstract {
    private MainMenu mainMenu;
    private NewModifyNewMenu newModifyNewMenu;

    public NewModifyMenu(UI actualUI) {
        super(actualUI);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("New/Modify Neural Network Menu");
        menuTable.addMenuItem(new MenuItem(0,"Create New Neural Network", "Create a new neural network from scratch"));
        menuTable.addMenuItem(new MenuItem(1,"Modify Existing Neural Network", "Modify parameters of an existing neural network"));
        menuTable.addMenuItem(new MenuItem(2,"Display Current Neural Network", "Display the structure and parameters of the current neural network"));
        menuTable.addMenuItem(new MenuItem(3,"Back to Main Menu", "Return to the main menu"));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) {
        switch (userChoice) {
            case 0:
                // Handle Create New Neural Network
                newModifyNewMenu.runMenu();
            case 1:
                // Handle Modify Existing Neural Network
                break;
            case 2:
                // Handle Display Current Neural Network
                break;
            case 3:
                // Handle Back to Main Menu
                mainMenu.runMenu();
            default:
                // Handle invalid choice
                invalidChoice();
                break;
        }
    }

    public void setMainMenu(MainMenu menu) {
        this.mainMenu = menu;
    }

    public void setNewModifyNewMenu(NewModifyNewMenu menu) {
        this.newModifyNewMenu = menu;
    }
}
