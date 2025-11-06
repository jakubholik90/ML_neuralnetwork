package pl.jakubholik90.menus;

import pl.jakubholik90.dto.ActivationFunctionsNames;
import pl.jakubholik90.dto.NeuralNetworkConfig;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.util.Arrays;
import java.util.Scanner;

public class ModifyNNMenu extends MenuAbstract {
    private MainMenu mainMenu;

    public ModifyNNMenu(UI actualUI, App app) {
        super(actualUI, app);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Modify Neural Network Menu","");
        menuTable.addMenuItem(new MenuItem(1,"Display Current Neural Network", "Display the structure and parameters of the current neural network"));
        menuTable.addMenuItem(new MenuItem(2,"Inputs", "Define the number of input neurons"));
        menuTable.addMenuItem(new MenuItem(3,"Hidden Layers", "Define the number of hidden layers and neurons in each layer"));
        menuTable.addMenuItem(new MenuItem(4,"Outputs", "Define the number of output neurons"));
        menuTable.addMenuItem(new MenuItem(5,"Activation Function - Output Layer", "Set activation function for output layer"));
        menuTable.addMenuItem(new MenuItem(6,"Activation Function - Hidden Layers", "Set activation function for hidden layers"));
        menuTable.addMenuItem(new MenuItem(0,"Back to Main Menu", "Return to the main menu"));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) {
        switch (userChoice) {
            case 1:
                // Handle Display Current Neural Network
                break;
            case 2:
                // Handle Inputs
                handleInputs();
            case 3:
                // Handle Hidden Layers
                handleHiddenLayers();
            case 4:
                // Handle Outputs
                handleOutputs();
            case 5:
                // Handle Activation Function - Output Layer
                handleActivationFunctionOutputLayer();
            case 6:
                // Handle Activation Function - Hidden Layers
                handleActivationFunctionHiddenLayers();
            case 0:
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

    private void handleInputs() {
        NeuralNetworkConfig config = app.getConfig();
        actualUI.displayMessage("Actual number of input neurons: " + config.getStructure()[0]);
        actualUI.displayMessage("Enter new number of input neurons (>1): ");
        Scanner scanner = new Scanner(System.in);
        int inputNeurons = Integer.valueOf(scanner.nextLine());
        config.setNumberOfInputs(inputNeurons);
        actualUI.displayMessage("Number of input neurons updated successfully to " + inputNeurons + ".");
        this.runMenu();
    }

    private void handleHiddenLayers() {
        NeuralNetworkConfig config = app.getConfig();
        int[] structureHiddenLayers = new int[config.getStructure().length - 2];
        for (int i = 1; i < config.getStructure().length-2; i++) {
            structureHiddenLayers[i-1] = config.getStructure()[i];
        }
        String additionalMessage = "Actual structure of hidden layers,  numbered from 1 to " + (structureHiddenLayers.length) + ": " + Arrays.toString(structureHiddenLayers);
        MenuTable menuTable = new MenuTable("Modify Hidden Layers Menu", additionalMessage);
        menuTable.addMenuItem(new MenuItem(1,"Insert", "Insert a new hidden layer at specified position"));
        menuTable.addMenuItem(new MenuItem(2, "Modify", "Modify the number of neurons in an existing hidden layer"));
        menuTable.addMenuItem(new MenuItem(3,"Delete", "Delete an existing hidden layer"));
        menuTable.addMenuItem(new MenuItem(0,"Back", ""));
        int menuChoice = actualUI.displayMenuAskChoice(menuTable);
        Scanner scanner = new Scanner(System.in);
        switch (menuChoice) {
            case 1:
                // Insert hidden layer
                actualUI.displayMessage("Enter position to insert new hidden layer (1 to " + (structureHiddenLayers.length + 1) + "): ");
                int positionToAdd = Integer.valueOf(scanner.nextLine());
                actualUI.displayMessage("Enter number of neurons for the new hidden layer (>0): ");
                int neuronsToAdd = Integer.valueOf(scanner.nextLine());
                config.insertHiddenLayer(positionToAdd, neuronsToAdd);
                actualUI.displayMessage("Hidden layer inserted successfully at position " + positionToAdd + " with " + neuronsToAdd + " neurons.");
                break;
            case 2:
                // Modify hidden layer
                actualUI.displayMessage("Enter position of hidden layer to modify (1 to " + (structureHiddenLayers.length) + "): ");
                int positionToModify = Integer.valueOf(scanner.nextLine());
                actualUI.displayMessage("Enter new number of neurons for hidden layer at position " + positionToModify + " (>0): ");
                int neuronsToModify = Integer.valueOf(scanner.nextLine());
                config.modifyHiddenLayer(positionToModify, neuronsToModify);
                actualUI.displayMessage("Hidden layer at position " + positionToModify + " modified successfully to " + neuronsToModify + " neurons.");
                break;
            case 3:
                // Delete hidden layer
                actualUI.displayMessage("Enter position of hidden layer to delete (1 to " + (structureHiddenLayers.length) + "): ");
                int positionToDelete = Integer.valueOf(scanner.nextLine());
                config.deleteHiddenLayer(positionToDelete);
                actualUI.displayMessage("Hidden layer at position " + positionToDelete + " deleted successfully.");
                break;
            case 0:
                // Back
                this.runMenu();
                break;
            default:
                invalidChoice();
                break;
        }
        String finalMessage = "Modified structure of hidden layers,  numbered from 1 to " + (structureHiddenLayers.length) + ": " + Arrays.toString(structureHiddenLayers);
        actualUI.displayMessage(finalMessage);
        this.runMenu();
    }

    private void handleOutputs() {
        NeuralNetworkConfig config = app.getConfig();
        actualUI.displayMessage("Actual number of output neurons: " + config.getStructure()[config.getStructure().length - 1]);
        actualUI.displayMessage("Enter new number of output neurons (>1): ");
        Scanner scanner = new Scanner(System.in);
        int outputNeurons = Integer.valueOf(scanner.nextLine());
        config.setNumberOfInputs(outputNeurons);
        actualUI.displayMessage("Number of output neurons updated successfully to " + outputNeurons + ".");
        this.runMenu();
    }

    private void handleActivationFunctionOutputLayer() {
        NeuralNetworkConfig config = app.getConfig();
        String menuMessage = "Actual activation function for output layer: " + config.getOutputLayerActivationFunction();
        MenuTable menuTable = new MenuTable("Set Activation Function - Output Layer Menu", menuMessage);
        menuTable.addMenuItem(new MenuItem(1, ActivationFunctionsNames.SIGMOID.name(), ""));
        menuTable.addMenuItem(new MenuItem(2, ActivationFunctionsNames.RELU.name(), ""));
        menuTable.addMenuItem(new MenuItem(3, ActivationFunctionsNames.LEAKY_RELU.name(), ""));
        menuTable.addMenuItem(new MenuItem(4, ActivationFunctionsNames.DUMMY.name(), ""));
        int menuChoice = actualUI.displayMenuAskChoice(menuTable);
        switch (menuChoice) {
            case 1:
                config.setOutputLayerActivationFunction(ActivationFunctionsNames.SIGMOID.name());
                break;
            case 2:
                config.setOutputLayerActivationFunction(ActivationFunctionsNames.RELU.name());
                break;
            case 3:
                config.setOutputLayerActivationFunction(ActivationFunctionsNames.LEAKY_RELU.name());
                break;
            case 4:
                config.setOutputLayerActivationFunction(ActivationFunctionsNames.DUMMY.name());
                break;
            default:
                invalidChoice();
                break;
        }
        actualUI.displayMessage("Activation function for output layer updated successfully to " + config.getOutputLayerActivationFunction().toString() + ".");
        this.runMenu();
    }

    private void handleActivationFunctionHiddenLayers() {
        NeuralNetworkConfig config = app.getConfig();
        String menuMessage = "Actual activation function for hidden layers: " + config.getHiddenLayerActivationFunction();
        MenuTable menuTable = new MenuTable("Set Activation Function - Hidden Layers Menu", menuMessage);
        menuTable.addMenuItem(new MenuItem(1, ActivationFunctionsNames.SIGMOID.name(), ""));
        menuTable.addMenuItem(new MenuItem(2, ActivationFunctionsNames.RELU.name(), ""));
        menuTable.addMenuItem(new MenuItem(3, ActivationFunctionsNames.LEAKY_RELU.name(), ""));
        menuTable.addMenuItem(new MenuItem(4, ActivationFunctionsNames.DUMMY.name(), ""));
        int menuChoice = actualUI.displayMenuAskChoice(menuTable);
        switch (menuChoice) {
            case 1:
                config.setHiddenLayerActivationFunction(ActivationFunctionsNames.SIGMOID.name());
                break;
            case 2:
                config.setHiddenLayerActivationFunction(ActivationFunctionsNames.RELU.name());
                break;
            case 3:
                config.setHiddenLayerActivationFunction(ActivationFunctionsNames.LEAKY_RELU.name());
                break;
            case 4:
                config.setHiddenLayerActivationFunction(ActivationFunctionsNames.DUMMY.name());
                break;
            default:
                invalidChoice();
                break;
        }
        actualUI.displayMessage("Activation function for hidden layers updated successfully to " + config.getHiddenLayerActivationFunction().toString() + ".");
        this.runMenu();
    }

}
