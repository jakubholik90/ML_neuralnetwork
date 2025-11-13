package pl.jakubholik90.menus;

import pl.jakubholik90.database.DataRecord;
import pl.jakubholik90.database.DataSet;
import pl.jakubholik90.database.DatabaseService;
import pl.jakubholik90.domains.NeuralNetwork;
import pl.jakubholik90.domains.TrainingDataRecord;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TrainNNMenu extends MenuAbstract {

    private MainMenu mainMenu;
    private DatabaseService dbService = new DatabaseService(actualUI, app);
    private DataSet dataSet = null;
    private boolean dataVerified = false;

    public TrainNNMenu(UI actualUI, App app) throws SQLException {
        super(actualUI, app);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Train Neural Network Menu","");
        menuTable.addMenuItem(new MenuItem(1,"Select data", "Select data set for training"));
        menuTable.addMenuItem(new MenuItem(2,"View data", "View current data set"));
        menuTable.addMenuItem(new MenuItem(3,"Verify data", "Verify if all data records in the set have the same size"));
        menuTable.addMenuItem(new MenuItem(4,"Load", "Load the structure and parameters of neural network from file"));
        menuTable.addMenuItem(new MenuItem(5,"Save", "Save the structure and parameters of neural network to file"));
        menuTable.addMenuItem(new MenuItem(6,"Start Training", "Start training with actual set of training data"));
        menuTable.addMenuItem(new MenuItem(7,"View actual structure", "Visualise the structure (activations) of neural network"));
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
                handleViewData();
                break;
            case 3:
                // Handle Verify data
                handleVerifyData();
                break;
            case 4:
                // Handle Load
                break;
            case 5:
                // Handle Save
                break;
            case 6:
                // Handle Start Training
                handleStartTraining();
                break;
            case 7:
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
            actualUI.displayMessage("Data set '" + dbSetName + "' selected for training. Verify the data before training.");
            this.dataVerified = false;
        }

        this.runMenu();
    }

    private void handleViewData() throws SQLException {
        if (this.dataSet != null) {
            actualUI.displayMessage("Current data set: " + this.dataSet.getName());
            dbService.previewDataRecords(dbService.getAllDataRecordsFromSet(this.dataSet));
        } else {
            actualUI.displayMessage("No data set selected. Please select a data set first.");
        }
        this.runMenu();
    }

    private void handleVerifyData() throws SQLException {
        if (this.dataSet != null) {
            actualUI.displayMessage("Verifying data set: " + this.dataSet.getName());
            boolean dbSetConsistentSizes = dbService.verifyDataSetSize(this.dataSet);
            boolean dbSetMatchesNNStructure = dbService.verifyDataSetMatchesStructure(this.dataSet, app.getNeuralNetwork());

            if (dbSetConsistentSizes) {
                if (dbSetMatchesNNStructure) {
                    actualUI.displayMessage("Data set verification successful. All records have the same size and match the NN structure.");
                    this.dataVerified = true;
                } else {
                    actualUI.displayMessage("Data set verification failed. Records sizes do not match the NN structure.");
                    this.dataVerified = false;
                    actualUI.displayMessage("NN Input size: " + app.getNeuralNetwork().getStructure()[0]
                            + ", Output size: " + app.getNeuralNetwork().getStructure()[app.getNeuralNetwork().getStructure().length - 1]);
                }
            } else {
                actualUI.displayMessage("Data set verification failed. Records have inconsistent sizes.");
                this.dataVerified = false;
            }

            if (!this.dataVerified) {
                HashMap<Integer, DataRecord> dataRecordHashMap = dbService.getAllDataRecordsFromSet(this.dataSet);
                for (Integer id : dataRecordHashMap.keySet()) {
                    DataRecord record = dataRecordHashMap.get(id);
                    actualUI.displayMessage("Record ID: " + id + ", Input size: " + record.inputData().length + ", Output size: " + record.outputData().length);}
            }
        } else {
            actualUI.displayMessage("No data set selected. Please select a data set first.");
        }
        this.runMenu();
    }

    private void handleStartTraining() throws SQLException {
        if (this.dataSet != null) {
            if (this.dataVerified) {
                actualUI.displayMessage("Starting training on data set: " + this.dataSet.getName());
                HashMap<Integer, DataRecord> trainingData = dbService.getAllDataRecordsFromSet(this.dataSet);
                NeuralNetwork neuralNetwork = app.getNeuralNetwork();
                ArrayList<TrainingDataRecord> trainingDataRecordList = new ArrayList<>(trainingData.size());
                for (Integer id : trainingData.keySet()) {
                    DataRecord record = trainingData.get(id);
                    TrainingDataRecord trainingDataRecord = new TrainingDataRecord(record.inputData(), record.outputData());
                    trainingDataRecordList.add(trainingDataRecord);
                }
                neuralNetwork.step2BackPropagation(trainingDataRecordList);
                actualUI.displayMessage("Training completed.");
            } else {
                actualUI.displayMessage("Data set not verified. Please verify the data before training.");
            }
        } else {
            actualUI.displayMessage("No data set selected. Please select a data set first.");
        }
        this.runMenu();
    }

}
