package pl.jakubholik90.menus;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

public class MainMenu extends MenuAbstract {

    private ModifyNNMenu modifyNNMenu;
    private TrainNNMenu trainNNMenu;
    private ManageDBMenu manageDBMenu;
    private PredictionMenu predictionMenu;
    private SaveLoadNNMenu saveLoadNNMenu;


    public MainMenu(UI actualUI, App app) {
        super(actualUI, app);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Main Menu","");
        menuTable.addMenuItem(new MenuItem(1,"Modify NN Settings", "Modify / display neural network settings"));
        menuTable.addMenuItem(new MenuItem(2,"Manage Database", "Manage training data from database"));
        menuTable.addMenuItem(new MenuItem(3,"Train NN", "Train the neural network with training data"));
        menuTable.addMenuItem(new MenuItem(4,"Run prediction", "Run a prediction with custom input"));
        menuTable.addMenuItem(new MenuItem(5,"Save/Load NN", "Save actual or load neural network from file"));
        menuTable.addMenuItem(new MenuItem(0,"Exit app", ""));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) {
        switch (userChoice) {
            case 1:
                // Handle Modify Neural Network
                modifyNNMenu.runMenu();
                break;
            case 2:
                // Handle Manage Training Data
                manageDBMenu.runMenu();
                break;
            case 3:
                // Handle Train Neural Network
                trainNNMenu.runMenu();
                break;
            case 4:
                // Handle Run prediction
                predictionMenu.runMenu();
                break;
            case 5:
                // Handle Save/Load Neural Network
                saveLoadNNMenu.runMenu();
                break;
            case 0:
                // Handle Exit
                actualUI.displayMessage("Thank you for using the Neural Network Application. Goodbye!");
                System.exit(0);
                break;
            default:
                // Handle invalid choice
                invalidChoice();
                break;
        }
    }


    public void setModifyNNMenu(ModifyNNMenu modifyNNMenu) {
        this.modifyNNMenu = modifyNNMenu;
    }

    public void setTrainNNMenu(TrainNNMenu trainNNMenu) {
        this.trainNNMenu = trainNNMenu;
    }

    public void setManageDBMenu(ManageDBMenu manageDBMenu) {
        this.manageDBMenu = manageDBMenu;
    }

    public void setPredictionMenu(PredictionMenu predictionMenu) {
        this.predictionMenu = predictionMenu;
    }

    public void setSaveLoadNNMenu(SaveLoadNNMenu saveLoadNNMenu) {
        this.saveLoadNNMenu = saveLoadNNMenu;
    }

}
