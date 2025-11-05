package pl.jakubholik90.menus;

import pl.jakubholik90.ui.UI;

public abstract class MenuAbstract {
    protected final UI actualUI;

    public MenuAbstract(UI actualUI) {
        this.actualUI = actualUI;
    }

    public abstract MenuTable create();

    public abstract void handleChoice(int userChoice);

    public final void runMenu() {
        MenuTable menuTable = create();
        int menuChoice = actualUI.displayMenuAskChoice(menuTable);
        handleChoice(menuChoice);
    }

    public final void invalidChoice() {
        actualUI.displayMessage("Invalid choice. Please try again.");
        this.runMenu();
    }
}
