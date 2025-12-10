package pl.jakubholik90.menus;

import pl.jakubholik90.controllers.CSVController;
import pl.jakubholik90.controllers.NeuralNetworkExportController;
import pl.jakubholik90.database.DataRecord;
import pl.jakubholik90.database.DataSet;
import pl.jakubholik90.database.DatabaseService;
import pl.jakubholik90.dto.NeuralNetworkSnapshotRecord;
import pl.jakubholik90.neuralnetwork.NeuralNetwork;
import pl.jakubholik90.neuralnetwork.NeuralNetworkConfig;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        MenuTable menuTable = new MenuTable("Manage Training Data Menu", "Browsing training data one by one or by sets.");
        menuTable.addMenuItem(new MenuItem(1, "View all data records", "Display all training data records in the database one by one"));
        menuTable.addMenuItem(new MenuItem(2, "View all data sets", "Display all data sets in the database"));
        menuTable.addMenuItem(new MenuItem(3, "Manage data record", "Insert, update or delete training data records one by one"));
        menuTable.addMenuItem(new MenuItem(4, "Manage data sets", "Rename, copy or delete data sets"));
        menuTable.addMenuItem(new MenuItem(5, "Import data set from csv", "Import data set from external csv file"));
        menuTable.addMenuItem(new MenuItem(0, "Back", "Return to the main menu"));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) throws SQLException {
        switch (userChoice) {
            case 1:
                // Handle View all data records
                handleViewAllDataRecords();
                break;
            case 2:
                // Handle View all data sets
                handleViewAllDataSets();
                break;
            case 3:
                // Handle Manage data record
                handleManageDataRecord();
                break;
            case 4:
                // Handle Manage data sets
                handleManageDataSets();
                break;
            case 5:
                //Hanlde Import from CSV
                handleImportFromCsv();
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
        menuTable.addMenuItem(new MenuItem(1, "Insert data record", "Add a new training data record to the database"));
        menuTable.addMenuItem(new MenuItem(2, "Update data record", "Modify an existing training data record from the database"));
        menuTable.addMenuItem(new MenuItem(3, "Delete data record", "Remove a training data record from the database"));
        menuTable.addMenuItem(new MenuItem(0, "Back", "Return to the Manage Database Menu"));

        int userChoice = actualUI.displayMenuAskChoice(menuTable);

        DataRecord dbRecord = null;
        int selectedId = -1;
        if (userChoice == 2 || userChoice == 3) {
            actualUI.displayMessage("Select existing data record with id:");
            selectedId = Integer.parseInt(actualUI.getUserInput());
            dbRecord = dbService.getDataRecordById(selectedId);
            actualUI.displayMessage("Current data record details:");
            dbService.previewSingleDataRecord(dbRecord);
        }

        switch (userChoice) {
            case 1:
                actualUI.displayMessage("Please provide the following details for the new data record:");
                actualUI.displayMessage("- Data Set Name");
                String dataSetName1 = actualUI.getUserInput();
                actualUI.displayMessage("- Input Values (comma-separated), for example: 0.5,1.2,3.4");
                String inputValues1 = actualUI.getUserInput();
                actualUI.displayMessage("- Output Values (comma-separated), for example: 1.0,0.0");
                String outputValues1 = actualUI.getUserInput();
                DataRecord dataRecordToInsert = new DataRecord(dataSetName1, dbService.parseStringToDoubleArray(inputValues1), dbService.parseStringToDoubleArray(outputValues1));
                dbService.insertDataRecord(dataRecordToInsert);
                break;
            case 2:
                actualUI.displayMessage("Please provide the new details for the new data record or type \"X\" to leave old one:");
                actualUI.displayMessage("- Data Set Name");
                String dataSetName2 = actualUI.getUserInput();
                actualUI.displayMessage("- Input Values (comma-separated), for example: 0.5,1.2,3.4");
                String inputValues2 = actualUI.getUserInput();
                actualUI.displayMessage("- Output Values (comma-separated), for example: 1.0,0.0");
                String outputValues2 = actualUI.getUserInput();
                String updatedDataSetName = dataSetName2.equalsIgnoreCase("X") ? dbRecord.setName() : dataSetName2;
                String updatedInputValues = inputValues2.equalsIgnoreCase("X") ? Arrays.toString(dbRecord.inputData()) : inputValues2;
                String updatedOutputValues = outputValues2.equalsIgnoreCase("X") ? Arrays.toString(dbRecord.outputData()) : outputValues2;
                DataRecord updatedDataRecord = new DataRecord(updatedDataSetName, dbService.parseStringToDoubleArray(updatedInputValues), dbService.parseStringToDoubleArray(updatedOutputValues));
                dbService.updateDataRecord(selectedId, updatedDataRecord);
                actualUI.displayMessage("Updated data record:");
                dbService.previewSingleDataRecord(dbService.getDataRecordById(selectedId));
                break;
            case 3:
                actualUI.displayMessage("Are you sure you want to delete this data record? Type Y to confirm.");
                String confirmation3 = actualUI.getUserInput();
                if (confirmation3.equalsIgnoreCase("Y")) {
                    dbService.deleteDataRecord(selectedId);
                    actualUI.displayMessage("Data record deleted successfully.");
                } else {
                    actualUI.displayMessage("Deletion cancelled.");
                }
                break;
            case 0:
                this.runMenu();
                break;
            default:
                invalidChoice();
                break;
        }
        this.runMenu();
    }

    private void handleManageDataSets() throws SQLException {
        MenuTable menuTable = new MenuTable("Manage Data Sets Menu", "Rename, copy or delete data sets.");
        menuTable.addMenuItem(new MenuItem(1, "Rename data set", "Change the name of an existing data set"));
        menuTable.addMenuItem(new MenuItem(2, "Copy data set", "Create a copy of an existing data set under a new name"));
        menuTable.addMenuItem(new MenuItem(3, "Delete data set", "Remove an entire data set and all its associated records from the database"));
        menuTable.addMenuItem(new MenuItem(0, "Back", "Return to the Manage Database Menu"));
        int userChoice = actualUI.displayMenuAskChoice(menuTable);

        DataSet dbSetByName = null;
        String dbSetName = "";
        if (userChoice != 0) {
            dbService.previewDataSetNames();
            actualUI.displayMessage("Enter the name of the data set to operate on:");
            dbSetName = actualUI.getUserInput();
            dbSetByName = dbService.getDataSetByName(dbSetName);
        }


        switch (userChoice) {
            case 1:
                actualUI.displayMessage("Enter the new name for the data set:");
                String newName1 = actualUI.getUserInput();
                dbService.renameDataSet(dbSetByName, newName1);
                actualUI.displayMessage("Data set renamed successfully.");
                break;
            case 2:
                actualUI.displayMessage("Enter the new name for the copied data set:");
                String newName2 = actualUI.getUserInput();
                dbService.copyDataSet(dbSetByName, newName2);
                actualUI.displayMessage("Data set copied successfully.");
                break;
            case 3:
                actualUI.displayMessage("Are you sure you want to delete this data set and all its records? Type Y to confirm.");
                String confirmation3 = actualUI.getUserInput();
                if (confirmation3.equalsIgnoreCase("Y")) {
                    dbService.deleteDataSetByName(dbSetName);
                    actualUI.displayMessage("Data set deleted successfully.");
                } else {
                    actualUI.displayMessage("Deletion cancelled.");
                }
                break;
            case 0:
                this.runMenu();
                break;
            default:
                invalidChoice();
                break;
        }
        this.runMenu();
    }

    private void handleImportFromCsv() throws SQLException {
        List<String> fileList = CSVController.listAllFiles();
        MenuTable loadCsvMenu = new MenuTable("Load CSV File Menu", "Select CSV file to be loaded");
        for (int i = 1; i < fileList.size() + 1; i++) {
            loadCsvMenu.addMenuItem(new MenuItem(i, fileList.get(i - 1), ""));
        }
        loadCsvMenu.addMenuItem(new MenuItem(0, "Back", "Return to the previous menu"));
        int choice = this.actualUI.displayMenuAskChoice(loadCsvMenu);

        boolean validChoice = false;

        if (choice == 0) {
            this.runMenu();
        } else {
            if (choice > 0 && choice <= fileList.size()) {
                validChoice = true;
            } else {
                validChoice = false;
                this.actualUI.displayMessage("Invalid choice, returning to previous menu");
                this.runMenu();
            }
        }

        if (validChoice) {
            String lineToRead = fileList.get(choice - 1);
            this.actualUI.displayMessage("Loading: " + lineToRead);
            List<Double[]> actualCsvAsList = CSVController.importCsvData(lineToRead);
            int actualCsvSize = actualCsvAsList.getFirst().length;
            this.actualUI.displayMessage("First Data Row: " + Arrays.toString(actualCsvAsList.getFirst()));
            this.actualUI.displayMessage("Insert set name:");
            String newSetName = this.actualUI.getUserInput();
            this.actualUI.displayMessage("Splitting between inputs and outputs. Insert number of inputs (<=" + actualCsvSize + ")");
            Integer userInput = Integer.valueOf(this.actualUI.getUserInput());

            if (!(userInput > actualCsvSize) && userInput > 0) {
                this.actualUI.displayMessage("Splitting " + actualCsvSize + " values into " + userInput + " inputs and " + (actualCsvSize - userInput) + " outputs");
                for (Double[] actualCsvRecord : actualCsvAsList) {
                    Double[] inputs = new Double[userInput];
                    Double[] outputs = new Double[actualCsvSize - userInput];
                    for (int i = 0; i < actualCsvSize; i++) {
                        if (i < userInput) {
                            inputs[i] = actualCsvRecord[i];
                        } else {
                            outputs[i - userInput] = actualCsvRecord[i];
                        }
                    }
                    DataRecord dataRecordToInsert = new DataRecord(newSetName, inputs, outputs);
                    dbService.insertDataRecord(dataRecordToInsert);
                    this.actualUI.displayMessage("Added following record to Database: ");
                    dbService.previewSingleDataRecord(dataRecordToInsert);
                }
            } else {
                this.actualUI.displayMessage("Invalid choice, returning to previous menu");
                this.runMenu();
            }

            this.runMenu();
        }
    }
}
