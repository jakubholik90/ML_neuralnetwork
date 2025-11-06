package pl.jakubholik90.menus;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

public class MainMenu extends MenuAbstract {

    private ModifyNNMenu modifyNNMenu;


    public MainMenu(UI actualUI, App app) {
        super(actualUI, app);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Main Menu","");
        menuTable.addMenuItem(new MenuItem(1,"Modify Neural Network", "Modify an existing neural network"));
        menuTable.addMenuItem(new MenuItem(2,"Save/Load Neural Network", "Save actual or load an existing neural network from file"));
        menuTable.addMenuItem(new MenuItem(3,"Display Neural Network", "Display the structure and parameters of the neural network"));
        menuTable.addMenuItem(new MenuItem(4,"Train Neural Network", "Train the neural network with training data"));
        menuTable.addMenuItem(new MenuItem(5,"Run prediction", "Run a prediction using the neural network"));
        menuTable.addMenuItem(new MenuItem(0,"Exit", "Exit the application"));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) {
        switch (userChoice) {
            case 1:
                // Handle Modify Neural Network
                modifyNNMenu.runMenu();
            case 2:
                // Handle Save/Load Neural Network
                break;
            case 3:
                // Handle Display Neural Network
                break;
            case 4:
                // Handle Train Neural Network
                break;
            case 5:
                // Handle Run prediction
                break;
            case 0:
                // Handle Exit
                break;
            default:
                // Handle invalid choice
                invalidChoice();
                break;
        }
    }


    public void setModifyNNMenu(ModifyNNMenu modifyNNMenu) {
        this.modifyNNMenu = modifyNNMenu;
    }

}
