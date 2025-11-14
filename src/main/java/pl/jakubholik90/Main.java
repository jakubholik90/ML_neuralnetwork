package pl.jakubholik90;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.ConsoleUI;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        App app = new App(new ConsoleUI());
        app.runApp();

    }
}