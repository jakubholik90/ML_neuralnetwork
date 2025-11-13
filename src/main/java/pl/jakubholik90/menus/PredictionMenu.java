package pl.jakubholik90.menus;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

public class PredictionMenu extends MenuAbstract {
    private MainMenu mainMenu;

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
                // handleSetInputData();
                break;
            case 2:
                // Handle Run Prediction
                // handleRunPrediction();
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
}
