package pl.jakubholik90.menus;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.sql.SQLException;

public abstract class MenuAbstract {
    protected final UI actualUI;
    protected final App app;

    public MenuAbstract(UI actualUI, App app) {
        this.actualUI = actualUI;
        this.app = app;
    }

    public abstract MenuTable create();

    public abstract void handleChoice(int userChoice) throws Exception;

    public final void runMenu() {
        MenuTable menuTable = create();
        int menuChoice = actualUI.displayMenuAskChoice(menuTable);
        try {handleChoice(menuChoice);
        } catch (Exception e) {
            actualUI.displayMessage("An error occurred: " + e.getMessage());
            this.runMenu();
        }
    }

    public final void invalidChoice() {
        actualUI.displayMessage("Invalid choice. Please enter another option number.");
        this.runMenu();
    }

}
