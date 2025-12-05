package pl.jakubholik90.ui;

import pl.jakubholik90.controllers.NeuralNetworkController;
import pl.jakubholik90.neuralnetwork.NeuralNetwork;
import pl.jakubholik90.dto.NeuralNetworkConfig;
import pl.jakubholik90.menus.*;

import java.sql.SQLException;

public class App {
    // internal objects
    // menus
    private MainMenu mainMenu;
    private ModifyNNMenu modifyNNMenu;
    private TrainNNMenu trainNNMenu;
    private ManageDBMenu manageDBMenu;
    private PredictionMenu predictionMenu;
    private SaveLoadNNMenu saveLoadNNMenu;
    // ui
    private UI actualUI;
    // neural network and its config
    private NeuralNetworkConfig currentConfig;
    private NeuralNetworkController controller;
    private NeuralNetwork currentNeuralNetwork;


    public App(UI actualUI) throws SQLException {
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

    private void initializeMenus() throws SQLException {
        // create new menus
        this.mainMenu = new MainMenu(actualUI,this);
        this.modifyNNMenu = new ModifyNNMenu(actualUI,this);
        this.trainNNMenu = new TrainNNMenu(actualUI,this);
        this.manageDBMenu = new ManageDBMenu(actualUI,this);
        this.predictionMenu = new PredictionMenu(actualUI,this);
        this.saveLoadNNMenu = new SaveLoadNNMenu(actualUI,this);


        // inject dependencies between menus
        //"main" menu connections
        this.mainMenu.setModifyNNMenu(this.modifyNNMenu); // from MainMenu to NewModifyMenu
        this.mainMenu.setTrainNNMenu(this.trainNNMenu); // from MainMenu to TrainNNMenu
        this.mainMenu.setManageDBMenu(this.manageDBMenu); // from MainMenu to ManageDBMenu
        this.mainMenu.setPredictionMenu(this.predictionMenu); // from MainMenu to PredictionMenu
        this.mainMenu.setSaveLoadNNMenu(this.saveLoadNNMenu); // from MainMenu to SaveLoadNNMenu
        // "new/modify" menu connections
        this.modifyNNMenu.setMainMenu(this.mainMenu); // from NewModifyMenu to MainMenu
        // "train" menu connections
        this.trainNNMenu.setMainMenu(this.mainMenu); // from NewModifyMenu to MainMenu
        // "manage db" menu connections
        this.manageDBMenu.setMainMenu(this.mainMenu); // from ManageDBMenu to MainMenu
        // "prediction" menu connections
        this.predictionMenu.setMainMenu(this.mainMenu); // from PredictionMenu to MainMenu
        this.predictionMenu.setTrainNNMenu(this.trainNNMenu); // from PredictionMenu to TrainNNMenu
        // "save/load nn" menu connections
        this.saveLoadNNMenu.setMainMenu(this.mainMenu); // from SaveLoadNNMenu to MainMenu
        this.saveLoadNNMenu.setTrainNNMenu(this.trainNNMenu); // from SaveLoadNNMenu to TrainNNMenu
    }

    public void setConfig (NeuralNetworkConfig config) {
        this.currentConfig = config;
    }

    public NeuralNetworkConfig getConfig() {
        if (this.currentConfig == null) {
            this.currentConfig = new NeuralNetworkConfig();
        }
        return this.currentConfig;
    }

    public NeuralNetwork getNeuralNetwork() {
        return this.currentNeuralNetwork;
    }

    public void  setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.currentNeuralNetwork = neuralNetwork;
    }

    public NeuralNetworkController getController() {
        return controller;
    }

    public void buildNeuralNetwork() {
        this.currentNeuralNetwork = controller.createNeuralNetworkFromConfig(this.currentConfig);
        this.currentNeuralNetwork.step0Build();
        actualUI.displayMessage("Neural Network successfully built.");
    }


}
