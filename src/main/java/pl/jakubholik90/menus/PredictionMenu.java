package pl.jakubholik90.menus;

import pl.jakubholik90.domains.NeuralNetwork;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.util.Arrays;

public class PredictionMenu extends MenuAbstract {
    private MainMenu mainMenu;
    private TrainNNMenu trainNNMenu;

    private Double[] inputData;
    private Double[] outputData;

    public PredictionMenu(UI actualUI, App app) {
        super(actualUI, app);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Prediction Menu", "");
        menuTable.addMenuItem(new MenuItem(1,"Set Input Data", "Set custom input data for prediction"));
        menuTable.addMenuItem(new MenuItem(2, "Run Prediction", "Run prediction with custom input"));
        menuTable.addMenuItem(new MenuItem(0, "Back to Main menu", ""));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) throws Exception {
        switch (userChoice) {
            case 1:
                // Handle Set Input Data
                handleSetInputData();
                break;
            case 2:
                // Handle Run Prediction
                handleRunPrediction();
                break;
            case 0:
                // Handle Back to Main menu
                mainMenu.runMenu();
                break;
        }
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setTrainNNMenu(TrainNNMenu trainNNMenu) {
        this.trainNNMenu = trainNNMenu;
    }

    private void handleSetInputData() {
        int inputSize = this.app.getNeuralNetwork().getStructure()[0];
        this.inputData = new Double[inputSize];
        actualUI.displayMessage("Please enter " + inputSize + " input values, every value in new line:");
        for (int i = 0; i < inputSize; i++) {
            String userInput = actualUI.getUserInput();
            try {
                double value = Double.parseDouble(userInput);
                this.inputData[i] = value;
            } catch (NumberFormatException e) {
                actualUI.displayMessage("Invalid input. Please enter a numeric value.");
                i--; // Decrement i to repeat this iteration
            }
        }
        actualUI.displayMessage("Input data set successfully.");
        actualUI.displayMessage("Input Data: " + Arrays.toString(this.inputData));
        this.runMenu();
    }

    private void handleRunPrediction() {
        if (this.inputData == null) {
            actualUI.displayMessage("Input data is not set. Please set input data first.");
            this.runMenu();
            return;
        }

        if (!this.trainNNMenu.isNetworkIsTrained()) {
            actualUI.displayMessage("Neural network is not trained. Please train the neural network first.");
            this.runMenu();
            return;
        }

        NeuralNetwork neuralNetwork = this.app.getNeuralNetwork();
        this.outputData = neuralNetwork.step1FeedForward(this.inputData);
        actualUI.displayMessage("Prediction completed for input data: " + Arrays.toString(this.inputData));
        actualUI.displayMessage("Output Data: " + Arrays.toString(this.outputData));
        this.runMenu();
    }
}
