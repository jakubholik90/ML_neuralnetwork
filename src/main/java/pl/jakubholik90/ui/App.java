package pl.jakubholik90.ui;

import pl.jakubholik90.controllers.NeuralNetworkController;
import pl.jakubholik90.domains.NeuralNetwork;
import pl.jakubholik90.dto.NeuralNetworkConfig;
import pl.jakubholik90.menus.MainMenu;
import pl.jakubholik90.menus.ModifyNNMenu;

public class App {
    // internal objects
    // menus
    private MainMenu mainMenu;
    private ModifyNNMenu modifyNNMenu;
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


        // inject dependencies between menus
        //"main" menu connections
        this.mainMenu.setModifyNNMenu(this.modifyNNMenu); // from MainMenu to NewModifyMenu

        // "new/modify" menu connections
        this.modifyNNMenu.setMainMenu(this.mainMenu); // from NewModifyMenu to MainMenu

    }

    public NeuralNetworkConfig getConfig() {
        if (this.currentConfig == null) {
            this.currentConfig = new NeuralNetworkConfig();
        }
        return this.currentConfig;
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
}
