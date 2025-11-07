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
        menuTable.addMenuItem(new MenuItem(1,"Modify Neural Network", "Modify / display neural network settings"));
        menuTable.addMenuItem(new MenuItem(2,"Save/Load Neural Network", "Save actual or load an existing neural network from file"));
        menuTable.addMenuItem(new MenuItem(3,"Visualise Neural Network", "Visualise the structure of the neural network"));
        menuTable.addMenuItem(new MenuItem(4,"Train Neural Network", "Train the neural network with training data"));
        menuTable.addMenuItem(new MenuItem(5,"Run prediction", "Run a prediction with custom input"));
        menuTable.addMenuItem(new MenuItem(0,"Exit app", ""));
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
                handleVisualise();
            case 4:
                // Handle Train Neural Network
                break;
            case 5:
                // Handle Run prediction
                break;
            case 0:
                // Handle Exit
                actualUI.displayMessage("Thank you for using the Neural Network Application. Goodbye!");
                System.exit(0);
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

    private void handleVisualise() {
        actualUI.displayNeuralNetwork(app.getNeuralNetwork(),true);
        this.runMenu();
    }

}
