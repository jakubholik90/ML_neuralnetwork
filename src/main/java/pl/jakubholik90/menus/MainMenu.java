package pl.jakubholik90.menus;

import pl.jakubholik90.ui.UI;

public class MainMenu extends MenuAbstract {

    private NewModifyMenu newModifyMenu;


    public MainMenu(UI actualUI) {
        super(actualUI);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Main Menu");
        menuTable.addMenuItem(new MenuItem(0,"New/Modify Neural Network", "Create a new or modify an existing neural network from scratch"));
        menuTable.addMenuItem(new MenuItem(1,"Save/Load Neural Network", "Save actual or load an existing neural network from file"));
        menuTable.addMenuItem(new MenuItem(2,"Display Neural Network", "Display the structure and parameters of the neural network"));
        menuTable.addMenuItem(new MenuItem(3,"Train Neural Network", "Train the neural network with training data"));
        menuTable.addMenuItem(new MenuItem(4,"Run prediction", "Run a prediction using the neural network"));
        menuTable.addMenuItem(new MenuItem(5,"Exit", "Exit the application"));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) {
        switch (userChoice) {
            case 0:
                // Handle New/Modify Neural Network
                newModifyMenu.runMenu();
            case 1:
                // Handle Save/Load Neural Network
                break;
            case 2:
                // Handle Display Neural Network
                break;
            case 3:
                // Handle Train Neural Network
                break;
            case 4:
                // Handle Run prediction
                break;
            case 5:
                // Handle Exit
                break;
            default:
                // Handle invalid choice
                invalidChoice();
                break;
        }
    }


    public void setNewModifyMenu(NewModifyMenu newModifyMenu) {
        this.newModifyMenu = newModifyMenu;
    }

}
