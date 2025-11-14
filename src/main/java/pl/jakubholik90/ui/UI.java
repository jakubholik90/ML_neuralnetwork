package pl.jakubholik90.ui;

import pl.jakubholik90.neuralnetwork.NeuralNetwork;
import pl.jakubholik90.menus.MenuTable;

public interface UI {

    public void clearScreen();

    public void displayNeuralNetwork(NeuralNetwork neuralNetwork, boolean showEmpty);

    public void displayMessage(String message);

    public int displayMenuAskChoice(MenuTable menuTable);

    public String getUserInput();

    public void displayPlot(Double[] xValues, Double[] yValues, String title, String xLabel, String yLabel);
}
