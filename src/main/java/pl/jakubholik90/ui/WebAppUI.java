package pl.jakubholik90.ui;

import pl.jakubholik90.neuralnetwork.NeuralNetwork;
import pl.jakubholik90.menus.MenuTable;

public class WebAppUI implements UI{

    @Override
    public void clearScreen() {
        //PLACEHOLDER - to be implemented in future
    }

    @Override
    public void displayNeuralNetwork(NeuralNetwork neuralNetwork, boolean showEmpty) {
        //PLACEHOLDER - to be implemented in future
    }

    @Override
    public void displayMessage(String message) {
        //PLACEHOLDER - to be implemented in future
    }

    @Override
    public int displayMenuAskChoice(MenuTable menuTable) {
        //PLACEHOLDER - to be implemented in future
        int userChoice = 0;
        return userChoice;
    }

    @Override
    public String getUserInput() {
        //PLACEHOLDER - to be implemented in future
        return "";
    }

    @Override
    public void displayPlot(Double[] xValues, Double[] yValues, String title, String xLabel, String yLabel) {
        //PLACEHOLDER - to be implemented in future
    }
}
