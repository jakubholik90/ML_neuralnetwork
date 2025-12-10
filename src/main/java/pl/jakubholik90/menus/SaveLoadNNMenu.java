package pl.jakubholik90.menus;

import pl.jakubholik90.controllers.NeuralNetworkExportController;
import pl.jakubholik90.neuralnetwork.NeuralNetworkConfig;
import pl.jakubholik90.dto.NeuralNetworkSnapshotRecord;
import pl.jakubholik90.neuralnetwork.NeuralNetwork;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SaveLoadNNMenu extends MenuAbstract {
    private MainMenu mainMenu;
    private TrainNNMenu trainNNMenu;

    public SaveLoadNNMenu(UI actualUI, App app) {
        super(actualUI, app);
    }

    public void setMainMenu(MainMenu menu) {
        this.mainMenu = menu;
    }

    public void setTrainNNMenu(TrainNNMenu menu) {
        this.trainNNMenu = menu;
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
        actualUI.displayMessage("Please enter NN name to save:");
        String nnName = actualUI.getUserInput();
        actualUI.displayMessage("Please enter comment (or leave empty):");
        String comment = actualUI.getUserInput();

        NeuralNetworkSnapshotRecord snapshot = NeuralNetworkExportController.createSnapshot(
                app.getConfig(),
                app.getNeuralNetwork(),
                nnName,
                trainNNMenu.getDataSet().getName(),
                comment);

        String fileName = NeuralNetworkExportController.exportNeuralNetwork(snapshot);
        actualUI.displayMessage("Neural Network saved to file: " + fileName);
        this.runMenu();
    }

    private void handleLoadNeuralNetwork() throws SQLException {
        List<String> fileList = NeuralNetworkExportController.listAllFiles();
        MenuTable loadNNmenu = new MenuTable("Load Neural Network Menu", "");
        for (int i = 1; i < fileList.size()+1; i++) {
            loadNNmenu.addMenuItem(new MenuItem(i,fileList.get(i-1),""));
        }
        loadNNmenu.addMenuItem(new MenuItem(0,"Back", "Return to the previous menu"));
        int choice = this.actualUI.displayMenuAskChoice(loadNNmenu);

        boolean validChoice = false;

        if (choice == 0) {
            this.runMenu();
        } else {
            if (choice >0 && choice <= fileList.size()) {
                validChoice = true;
            } else {
                validChoice = false;
                this.actualUI.displayMessage("Invalid choice, returning to previous menu");
                this.runMenu();
            }
        }

        if (validChoice) {
            String lineToRead = fileList.get(choice-1);
            this.actualUI.displayMessage("Loading: " + lineToRead);
            NeuralNetworkSnapshotRecord importSnapshot = NeuralNetworkExportController.importNeuralNetwork(lineToRead);
            this.actualUI.displayMessage("--Basic Data--");
            this.actualUI.displayMessage("Name: " + importSnapshot.name());
            this.actualUI.displayMessage("Comment: " + importSnapshot.userComment());
            this.actualUI.displayMessage("--General Properties--");
            this.actualUI.displayMessage("Structure: " + Arrays.toString(importSnapshot.structure()));
            this.actualUI.displayMessage("Training Data Set: " + importSnapshot.trainingDataSetName());

            NeuralNetwork loadedNeuralNetwork = this.app.getController().restoreNeuralNetworkFromSnapshot(importSnapshot);
            NeuralNetworkConfig loadedConfig = new NeuralNetworkConfig();
            loadedConfig.setStructure(importSnapshot.structure());
            loadedConfig.setNumberOfIterations(importSnapshot.numberOfIterations());
            loadedConfig.setEta(importSnapshot.eta());
            loadedConfig.setOutputLayerActivationFunction(importSnapshot.outputLayerActivationFunction());
            loadedConfig.setHiddenLayerActivationFunction(importSnapshot.hiddenLayerActivationFunction());
            try {
                this.app.setNeuralNetwork(loadedNeuralNetwork);
                this.app.setConfig(loadedConfig);
                this.trainNNMenu.setAsTrainedWithDataset(importSnapshot.trainingDataSetName());
                this.actualUI.displayMessage("NN loded sucessfully");
            } catch (Exception e) {
                this.actualUI.displayMessage("NN not loaded");
            }


        }




        this.runMenu();
    }

}
