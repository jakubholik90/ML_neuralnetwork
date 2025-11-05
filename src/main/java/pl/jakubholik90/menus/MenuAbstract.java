package pl.jakubholik90.menus;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

public abstract class MenuAbstract {
    protected final UI actualUI;
    protected final App app;

    public MenuAbstract(UI actualUI, App app) {
        this.actualUI = actualUI;
        this.app = app;
    }

    public abstract MenuTable create();

    public abstract void handleChoice(int userChoice);

    public final void runMenu() {
        MenuTable menuTable = create();
        int menuChoice = actualUI.displayMenuAskChoice(menuTable);
        handleChoice(menuChoice);
    }

    public final void invalidChoice() {
        actualUI.displayMessage("Invalid choice. Please enter another option number.");
        this.runMenu();
    }

}
