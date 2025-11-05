package pl.jakubholik90.menus;

import pl.jakubholik90.ui.UI;

public class NewModifyNewMenu extends MenuAbstract {
    private NewModifyMenu newModifyMenu;

    public NewModifyNewMenu(UI actualUI) {
        super(actualUI);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Create New Neural Network Menu");
        menuTable.addMenuItem(new MenuItem(0,"Inputs", "Define the number of input neurons"));
        menuTable.addMenuItem(new MenuItem(1,"Hidden Layers", "Define the number of hidden layers and neurons in each layer"));
        menuTable.addMenuItem(new MenuItem(2,"Outputs", "Define the number of output neurons"));
        menuTable.addMenuItem(new MenuItem(3,"Back", "Return to the previous menu"));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) {
        switch (userChoice) {
            case 0:
                // Handle Define Inputs
                break;
            case 1:
                // Handle Define Hidden Layers
                break;
            case 2:
                // Handle Define Outputs
                break;
            case 3:
                // Handle Back to Main Menu
                newModifyMenu.runMenu();
            default:
                // Handle invalid choice
                invalidChoice();
                break;
        }
    }

    public void setNewModifyMenu(NewModifyMenu menu) {
        this.newModifyMenu = menu;
    }
}
