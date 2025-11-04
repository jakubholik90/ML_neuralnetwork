package pl.jakubholik90.ui;

import pl.jakubholik90.domains.NeuralNetwork;
import pl.jakubholik90.menus.MenuTable;

public interface UI {

    public void clearScreen();

    public void displayNeuralNetwork(NeuralNetwork neuralNetwork, Double[] inputData, boolean showEmpty);

    public void displayMessage(String message);

    public int displayMenuAskChoice(MenuTable menuTable);
}
