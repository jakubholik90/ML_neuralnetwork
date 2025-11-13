package pl.jakubholik90;

import pl.jakubholik90.domains.ActivationFunctions;
import pl.jakubholik90.domains.NeuralNetwork;
import pl.jakubholik90.domains.TrainingDataRecord;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.ConsoleUI;
import pl.jakubholik90.ui.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws SQLException {
        App app = new App(new ConsoleUI());
        app.runApp();

    }
}