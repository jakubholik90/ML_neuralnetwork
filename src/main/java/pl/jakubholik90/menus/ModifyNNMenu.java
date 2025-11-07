package pl.jakubholik90.menus;

import pl.jakubholik90.domains.ActivationFunctions;
import pl.jakubholik90.dto.ActivationFunctionsEnum;
import pl.jakubholik90.dto.NeuralNetworkConfig;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.util.Arrays;

public class ModifyNNMenu extends MenuAbstract {
    private MainMenu mainMenu;

    public ModifyNNMenu(UI actualUI, App app) {
        super(actualUI, app);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Modify Neural Network Menu","");
        menuTable.addMenuItem(new MenuItem(1,"Display Settings", "Display the structure and parameters of the current neural network"));
        menuTable.addMenuItem(new MenuItem(2,"Inputs", "Define the number of input neurons"));
        menuTable.addMenuItem(new MenuItem(3,"Hidden Layers", "Define the number of hidden layers and neurons in each layer"));
        menuTable.addMenuItem(new MenuItem(4,"Outputs", "Define the number of output neurons"));
        menuTable.addMenuItem(new MenuItem(5,"Activation Function - Hidden Layers", "Set activation function for hidden layers"));
        menuTable.addMenuItem(new MenuItem(6,"Activation Function - Output Layer", "Set activation function for output layer"));
        menuTable.addMenuItem(new MenuItem(7,"Eta", "Define the learning rate (eta) for training the neural network"));
        menuTable.addMenuItem(new MenuItem(8,"Iterations", "Define the number of training iterations for the neural network"));
        menuTable.addMenuItem(new MenuItem(0,"Update and Back", "Update neural network with current config. and return to the main menu"));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) {
        switch (userChoice) {
            case 1:
                // Handle Display Current Neural Network
                handleDisplay();
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
                // Handle Activation Function - Hidden Layers
                handleActivationFunctionHiddenLayers();
            case 6:
                // Handle Activation Function - Output Layer
                handleActivationFunctionOutputLayer();
            case 7:
                // Handle Eta
                handleEta();
            case 8:
                // Handle Iterations
                handleIterations();
            case 0:
                // Handle Back to Main Menu
                app.buildNeuralNetwork();
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

    private void handleDisplay() {
        NeuralNetworkConfig config = app.getConfig();
        if (config == null) {
            actualUI.displayMessage("No Neural Network configuration found.");
            return;
        }
        actualUI.displayMessage("Current Neural Network Configuration:");
        actualUI.displayMessage("- Structure: " + Arrays.toString(config.getStructure()));
        actualUI.displayMessage("- Number of iterations: " + config.getNumberOfIterations());
        actualUI.displayMessage("- Learning rate (eta): " + config.getEta());
        actualUI.displayMessage("- Activation function - hidden layers: " + config.getNameActivationFunction(config.getHiddenLayerActivationFunction()));
        actualUI.displayMessage("- Activation function - output layer: " + config.getNameActivationFunction(config.getOutputLayerActivationFunction()));
        this.runMenu();
    }


    private void handleInputs() {
        NeuralNetworkConfig config = app.getConfig();
        actualUI.displayMessage("Actual number of input neurons: " + config.getStructure()[0]);
        actualUI.displayMessage("Enter new number of input neurons (>1): ");
        int inputNeurons = Integer.valueOf(actualUI.getUserInput());
        config.setNumberOfInputs(inputNeurons);
        actualUI.displayMessage("Number of input neurons updated successfully to " + inputNeurons + ".");
        this.runMenu();
    }

    private void handleHiddenLayers() {
        NeuralNetworkConfig config = app.getConfig();
        int[] structureHiddenLayers = new int[config.getStructure().length - 2];
        for (int i = 1; i < config.getStructure().length-1; i++) {
            structureHiddenLayers[i-1] = config.getStructure()[i];
        }
        String additionalMessage = "Actual structure of hidden layers,  numbered from 1 to " + (structureHiddenLayers.length) + ": " + Arrays.toString(structureHiddenLayers);
        MenuTable menuTable = new MenuTable("Modify Hidden Layers Menu", additionalMessage);
        menuTable.addMenuItem(new MenuItem(1,"Insert", "Insert a new hidden layer at specified position"));
        menuTable.addMenuItem(new MenuItem(2, "Modify", "Modify the number of neurons in an existing hidden layer"));
        menuTable.addMenuItem(new MenuItem(3,"Delete", "Delete an existing hidden layer"));
        menuTable.addMenuItem(new MenuItem(0,"Back", ""));
        int menuChoice = actualUI.displayMenuAskChoice(menuTable);
        switch (menuChoice) {
            case 1:
                // Insert hidden layer
                actualUI.displayMessage("Enter position to insert new hidden layer (1 to " + (structureHiddenLayers.length + 1) + "): ");
                int positionToAdd = Integer.valueOf(actualUI.getUserInput());
                actualUI.displayMessage("Inserting hidden layer at position: "  + positionToAdd);
                actualUI.displayMessage("Enter number of neurons for the new hidden layer (>0): ");
                int neuronsToAdd = Integer.valueOf(actualUI.getUserInput());
                config.insertHiddenLayer(positionToAdd, neuronsToAdd);
                actualUI.displayMessage("Hidden layer inserted successfully at position " + positionToAdd + " with " + neuronsToAdd + " neurons.");
                break;
            case 2:
                // Modify hidden layer
                actualUI.displayMessage("Enter position of hidden layer to modify (1 to " + (structureHiddenLayers.length) + "): ");
                int positionToModify = Integer.valueOf(actualUI.getUserInput());
                actualUI.displayMessage("Enter new number of neurons for hidden layer at position " + positionToModify + " (>0): ");
                int neuronsToModify = Integer.valueOf(actualUI.getUserInput());
                config.modifyHiddenLayer(positionToModify, neuronsToModify);
                actualUI.displayMessage("Hidden layer at position " + positionToModify + " modified successfully to " + neuronsToModify + " neurons.");
                break;
            case 3:
                // Delete hidden layer
                if (config.getStructure().length <=3) {
                    actualUI.displayMessage("Cannot delete hidden layer. At least one hidden layer must remain.");
                    handleHiddenLayers();
                    break;
                }
                actualUI.displayMessage("Enter position of hidden layer to delete (1 to " + (structureHiddenLayers.length) + "): ");
                int positionToDelete = Integer.valueOf(actualUI.getUserInput());
                actualUI.displayMessage("Deleting hidden layer at position: "  + positionToDelete);
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
        int[] modifiedStructureHiddenlayers = new int[config.getStructure().length - 2];
        for (int i = 1; i < config.getStructure().length-1; i++) {
            modifiedStructureHiddenlayers[i-1] = config.getStructure()[i];
        }
        String finalMessage = "Modified structure of hidden layers numbered from 1 to " + (modifiedStructureHiddenlayers.length) + ": " + Arrays.toString(modifiedStructureHiddenlayers);
        actualUI.displayMessage(finalMessage);
        this.runMenu();
    }

    private void handleOutputs() {
        NeuralNetworkConfig config = app.getConfig();
        actualUI.displayMessage("Actual number of output neurons: " + config.getStructure()[config.getStructure().length - 1]);
        actualUI.displayMessage("Enter new number of output neurons (>1): ");
        int outputNeurons = Integer.valueOf(actualUI.getUserInput());
        config.setNumberOfOutputs(outputNeurons);
        actualUI.displayMessage("Number of output neurons updated successfully to " + outputNeurons + ".");
        this.runMenu();
    }

    private void handleActivationFunctionOutputLayer() {
        NeuralNetworkConfig config = app.getConfig();
        String menuMessage = "Actual activation function for output layer: " + config.getNameActivationFunction(config.getOutputLayerActivationFunction());
        MenuTable menuTable = new MenuTable("Set Activation Function - Output Layer Menu", menuMessage);
        menuTable.addMenuItem(new MenuItem(1, ActivationFunctionsEnum.SIGMOID.name(), ""));
        menuTable.addMenuItem(new MenuItem(2, ActivationFunctionsEnum.RELU.name(), ""));
        menuTable.addMenuItem(new MenuItem(3, ActivationFunctionsEnum.LEAKY_RELU.name(), ""));
        menuTable.addMenuItem(new MenuItem(4, ActivationFunctionsEnum.DUMMY.name(), ""));
        int menuChoice = actualUI.displayMenuAskChoice(menuTable);
        switch (menuChoice) {
            case 1:
                config.setOutputLayerActivationFunction(ActivationFunctionsEnum.SIGMOID.name());
                break;
            case 2:
                config.setOutputLayerActivationFunction(ActivationFunctionsEnum.RELU.name());
                break;
            case 3:
                config.setOutputLayerActivationFunction(ActivationFunctionsEnum.LEAKY_RELU.name());
                break;
            case 4:
                config.setOutputLayerActivationFunction(ActivationFunctionsEnum.DUMMY.name());
                break;
            default:
                invalidChoice();
                break;
        }
        actualUI.displayMessage("Activation function for output layer updated successfully to " + config.getNameActivationFunction(config.getOutputLayerActivationFunction()) + ".");
        this.runMenu();
    }

    private void handleActivationFunctionHiddenLayers() {
        NeuralNetworkConfig config = app.getConfig();
        String menuMessage = "Actual activation function for hidden layer: " + config.getNameActivationFunction(config.getHiddenLayerActivationFunction());
        MenuTable menuTable = new MenuTable("Set Activation Function - Hidden Layers Menu", menuMessage);
        menuTable.addMenuItem(new MenuItem(1, ActivationFunctionsEnum.SIGMOID.name(), ""));
        menuTable.addMenuItem(new MenuItem(2, ActivationFunctionsEnum.RELU.name(), ""));
        menuTable.addMenuItem(new MenuItem(3, ActivationFunctionsEnum.LEAKY_RELU.name(), ""));
        menuTable.addMenuItem(new MenuItem(4, ActivationFunctionsEnum.DUMMY.name(), ""));
        int menuChoice = actualUI.displayMenuAskChoice(menuTable);
        switch (menuChoice) {
            case 1:
                config.setHiddenLayerActivationFunction(ActivationFunctionsEnum.SIGMOID.name());
                break;
            case 2:
                config.setHiddenLayerActivationFunction(ActivationFunctionsEnum.RELU.name());
                break;
            case 3:
                config.setHiddenLayerActivationFunction(ActivationFunctionsEnum.LEAKY_RELU.name());
                break;
            case 4:
                config.setHiddenLayerActivationFunction(ActivationFunctionsEnum.DUMMY.name());
                break;
            default:
                invalidChoice();
                break;
        }
        actualUI.displayMessage("Activation function for hidden layers updated successfully to " + config.getNameActivationFunction(config.getHiddenLayerActivationFunction()) + ".");
        this.runMenu();
    }

    private void handleEta() {
        NeuralNetworkConfig config = app.getConfig();
        actualUI.displayMessage("Actual learning rate (eta): " + config.getEta());
        actualUI.displayMessage("Enter new learnning rate (eta) (0.0 < eta <= 1.0):");
        double eta = Double.valueOf(actualUI.getUserInput());
        config.setEta(eta);
        actualUI.displayMessage("Learning rate updated successfully to " + eta + ".");
        this.runMenu();
    }

    private void handleIterations() {
        NeuralNetworkConfig config = app.getConfig();
        actualUI.displayMessage("Actual number of iterations: " + config.getNumberOfIterations());
        actualUI.displayMessage("Enter new number of iterations > 0:");
        int iterations = Integer.valueOf(actualUI.getUserInput());
        config.setNumberOfIterations(iterations);
        actualUI.displayMessage("Number of iterations updated successfully to " + iterations + ".");
        this.runMenu();
    }

}
