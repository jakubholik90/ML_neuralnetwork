package pl.jakubholik90.menus;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

public class SaveLoadNNMenu extends MenuAbstract {
    private MainMenu mainMenu;

    public SaveLoadNNMenu(UI actualUI, App app) {
        super(actualUI, app);
    }

    public void setMainMenu(MainMenu menu) {
        this.mainMenu = menu;
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Save/Load Neural Network Menu","");
        menuTable.addMenuItem(new MenuItem(1,"Save Neural Network", "Save the current neural network to a file"));
        menuTable.addMenuItem(new MenuItem(2,"Load Neural Network", "Load a neural network from a file"));
        menuTable.addMenuItem(new MenuItem(0,"Back", "Return to the main menu"));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) throws Exception {
        switch (userChoice) {
            case 1:
                // Handle Save Neural Network
                handleSaveNeuralNetwork();
                break;
            case 2:
                // Handle Load Neural Network
                handleLoadNeuralNetwork();
                break;
            case 0:
                // Handle Back
                mainMenu.runMenu();
                break;
            default:
                // Handle invalid choice
                invalidChoice();
                break;
        }
    }

    private void handleSaveNeuralNetwork() {
        // Implementation for saving the neural network

        actualUI.displayMessage("Saving Neural Network... (Functionality not yet implemented)");
    }

    private void handleLoadNeuralNetwork() {
        // Implementation for loading the neural network
        actualUI.displayMessage("Loading Neural Network... (Functionality not yet implemented)");
    }
}
