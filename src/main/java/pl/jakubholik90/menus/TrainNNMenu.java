package pl.jakubholik90.menus;

import pl.jakubholik90.database.DataSet;
import pl.jakubholik90.database.DatabaseService;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.sql.SQLException;

public class TrainNNMenu extends MenuAbstract {

    private MainMenu mainMenu;

    private DatabaseService dbService = new DatabaseService(actualUI, app);

    private DataSet dataSet = null;

    public TrainNNMenu(UI actualUI, App app) throws SQLException {
        super(actualUI, app);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Train Neural Network Menu","");
        menuTable.addMenuItem(new MenuItem(1,"Select data", "Select data set for training"));
        menuTable.addMenuItem(new MenuItem(2,"View data", "View current data set"));
        menuTable.addMenuItem(new MenuItem(3,"Load", "Load the structure and parameters of neural network from file"));
        menuTable.addMenuItem(new MenuItem(4,"Save", "Save the structure and parameters of neural network to file"));
        menuTable.addMenuItem(new MenuItem(5,"Start Training", "Start training with actual set of training data"));
        menuTable.addMenuItem(new MenuItem(6,"View actual structure", "Visualise the structure (activations) of neural network"));
        menuTable.addMenuItem(new MenuItem(0,"Back to Main menu", ""));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) throws SQLException {
        switch (userChoice) {
            case 1:
                // Handle Select data
                handleSelectData();
                break;
            case 2:
                // Handle View data
                break;
            case 3:
                // Handle Load
                break;
            case 4:
                // Handle Save
                break;
            case 5:
                // Handle Start Training
                break;
            case 6:
                // Handle View actual structure
                break;
            case 0:
                // Handle Back to Main menu
                mainMenu.runMenu();
                break;
            default:
                // Handle invalid choice
                invalidChoice();
                break;
        }

    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    private void handleSelectData() throws SQLException {
        if (this.dataSet != null) {
            actualUI.displayMessage("Current data set: " + this.dataSet.getName());
        } else {
            actualUI.displayMessage("No data set selected.");
        }


        dbService.previewDataSetNames();
        actualUI.displayMessage("Enter the name of the data set to operate on:");
        String dbSetName = actualUI.getUserInput();
        DataSet dbSetByName = dbService.getDataSetByName(dbSetName);

        if (dbSetByName != null) {
            this.dataSet = dbSetByName;
            actualUI.displayMessage("Data set '" + dbSetName + "' selected for training.");
        }


        this.runMenu();
    }
}
