package pl.jakubholik90.menus;

import pl.jakubholik90.database.DataRecord;
import pl.jakubholik90.database.DataSet;
import pl.jakubholik90.database.DatabaseService;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.sql.SQLException;
import java.util.HashMap;

public class ManageDBMenu extends MenuAbstract {

    private MainMenu mainMenu;

    private DatabaseService dbService = new DatabaseService(actualUI, app);

    public ManageDBMenu(UI actualUI, App app) throws SQLException {
        super(actualUI, app);
    }

    public void setMainMenu(MainMenu menu) {
        this.mainMenu = menu;
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Manage Training Data Menu","Browsing training data one by one or by sets.");
        menuTable.addMenuItem(new MenuItem(1,"View all data records", "Display all training data records in the database one by one"));
        menuTable.addMenuItem(new MenuItem(2,"View all data sets", "Display all data sets in the database"));
        menuTable.addMenuItem(new MenuItem(3,"Manage data record", "Insert, update or delete training data records one by one"));
        menuTable.addMenuItem(new MenuItem(4,"Manage data sets", "Rename, copy or delete data sets"));
        menuTable.addMenuItem(new MenuItem(0,"Back", "Return to the main menu"));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) throws SQLException {
        switch (userChoice) {
            case 1:
                // Handle View all data records
                handleViewAllDataRecords();
            case 2:
                // Handle View all data sets
                handleViewAllDataSets();
            case 3:
                // Handle Manage data record
                handleManageDataRecord();
            case 4:
                // Handle Manage data sets
                break;
            case 0:
                // Handle Back
                mainMenu.runMenu();
                break;
            default:
                // Handle invalid choice
                invalidChoice();
                break;
        }

    }

    private void handleViewAllDataSets() throws SQLException {
        HashMap<Integer, DataSet> allDataSets = dbService.getAllDataSets();
        dbService.previewDataSets(allDataSets);
        this.runMenu();
    }

    private void handleViewAllDataRecords() throws SQLException {
        HashMap<Integer, DataRecord> allDataRecords = dbService.getAllDataRecords();
        dbService.previewDataRecords(allDataRecords);
        this.runMenu();
    }

    private void handleManageDataRecord() throws SQLException {
        MenuTable menuTable = new MenuTable("Manage Data Record Menu", "Insert, update or delete training data records one by one.");
        menuTable.addMenuItem(new MenuItem(1,"Insert data record", "Add a new training data record to the database"));
        menuTable.addMenuItem(new MenuItem(2,"Update data record", "Modify an existing training data record from the database"));
        menuTable.addMenuItem(new MenuItem(3,"Delete data record", "Remove a training data record from the database"));
        menuTable.addMenuItem(new MenuItem(0,"Back", "Return to the Manage Database Menu"));

        int userChoice = actualUI.displayMenuAskChoice(menuTable);
        switch (userChoice) {
            case 1:
                actualUI.displayMessage("Please provide the following details for the new data record:");
                actualUI.displayMessage("- Data Set Name");
                String dataSetName = actualUI.getUserInput();
                actualUI.displayMessage("- Input Values (comma-separated), for example: 0.5,1.2,3.4");
                String inputValues = actualUI.getUserInput();
                actualUI.displayMessage("- Output Values (comma-separated), for example: 1.0,0.0");
                String outputValues = actualUI.getUserInput();
                DataRecord dataRecordToInsert = new DataRecord(dataSetName, dbService.parseStringToDoubleArray(inputValues), dbService.parseStringToDoubleArray(outputValues));
                dbService.insertDataRecord(dataRecordToInsert);
                break;
            case 2:
                // dbService.updateDataRecord();
                break;
            case 3:
                // dbService.deleteDataRecord();
                break;
            case 0:
                // Back to ManageDBMenu
                this.runMenu();
                return;
            default:
                invalidChoice();
                break;
        }

        this.runMenu();

    }


}
