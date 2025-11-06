package pl.jakubholik90.ui;

import pl.jakubholik90.controllers.NeuralNetworkController;
import pl.jakubholik90.domains.NeuralNetwork;
import pl.jakubholik90.dto.NeuralNetworkConfig;
import pl.jakubholik90.menus.MainMenu;
import pl.jakubholik90.menus.ModifyNNMenu;
import pl.jakubholik90.menus.NewModifyNewMenu;

import java.util.Arrays;

public class App {
    // internal objects
    // menus
    private MainMenu mainMenu;
    private ModifyNNMenu modifyNNMenu;
    private NewModifyNewMenu newModifyNewMenu;
    // ui
    private UI actualUI;
    // neural network and its config
    private NeuralNetworkConfig currentConfig;
    private NeuralNetworkController controller;


    public App(UI actualUI) {
        this.actualUI = actualUI;
        this.currentConfig = new NeuralNetworkConfig();
        this.controller = new NeuralNetworkController();
        this.initializeMenus();
    }

    public void runApp() {
        actualUI.displayMessage("Welcome to the Neural Network Application!");
        this.buildNeuralNetwork();
        this.mainMenu.runMenu();
    }

    private void initializeMenus() {
        // create new menus
        this.mainMenu = new MainMenu(actualUI,this);
        this.modifyNNMenu = new ModifyNNMenu(actualUI,this);
        this.newModifyNewMenu = new NewModifyNewMenu(actualUI,this);


        // inject dependencies between menus
        //"main" menu connections
        this.mainMenu.setModifyNNMenu(this.modifyNNMenu); // from MainMenu to NewModifyMenu

        // "new/modify" menu connections
        this.modifyNNMenu.setMainMenu(this.mainMenu); // from NewModifyMenu to MainMenu

        // "new/modify-new" menu connections
        this.newModifyNewMenu.setNewModifyMenu(this.modifyNNMenu); // from NewModifyNewMenu to NewModifyMen
    }

    public NeuralNetworkConfig getConfig() {
        if (this.currentConfig == null) {
            this.currentConfig = new NeuralNetworkConfig();
        }
        return this.currentConfig;
    }

    private void resetConfig() {
        this.currentConfig = new NeuralNetworkConfig();
    }

    private void buildNeuralNetwork() {
        if (this.currentConfig==null) {
            actualUI.displayMessage("Neural Network configuration not found, cannot build neural network.");
            return;
        }

        NeuralNetwork nn = controller.createNeuralNetworkFromConfig(this.currentConfig);
        nn.step0Build();
        actualUI.displayMessage("Neural Network successfully created.");
    }

    private void displayCurrentConfig() {
        if (this.currentConfig == null) {
            actualUI.displayMessage("No Neural Network configuration found.");
            return;
        }
        actualUI.displayMessage("Current Neural Network Configuration:");
        actualUI.displayMessage("- Structure: " + Arrays.toString(this.currentConfig.getStructure()));
        actualUI.displayMessage("- Number of iterations: " + this.currentConfig.getNumberOfIterations());
        actualUI.displayMessage("- Learning rate (eta): " + this.currentConfig.getEta());
        actualUI.displayMessage("- Activation function - hidden layers: " + this.currentConfig.getHiddenLayerActivationFunction());
        actualUI.displayMessage("- Activation function - output layer: " + this.currentConfig.getOutputLayerActivationFunction());

    }


}
