package pl.jakubholik90.menus;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

public class MainMenu extends MenuAbstract {

    private ModifyNNMenu modifyNNMenu;
    private TrainNNMenu trainNNMenu;
    private ManageDBMenu manageDBMenu;


    public MainMenu(UI actualUI, App app) {
        super(actualUI, app);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Main Menu","");
        menuTable.addMenuItem(new MenuItem(1,"Modify Neural Network", "Modify / display neural network settings"));
        menuTable.addMenuItem(new MenuItem(2,"Manage Training Data", "Manage training data from database"));
        menuTable.addMenuItem(new MenuItem(3,"Visualise Neural Network", "Visualise the structure of the neural network"));
        menuTable.addMenuItem(new MenuItem(4,"Train Neural Network", "Train the neural network with training data"));
        menuTable.addMenuItem(new MenuItem(5,"Run prediction", "Run a prediction with custom input"));
        menuTable.addMenuItem(new MenuItem(6,"Save/Load Neural network", "Save actual or load neural network from file"));
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
                handleVisualise();
                break;
            case 4:
                // Handle Train Neural Network
                trainNNMenu.runMenu();
                break;
            case 5:
                // Handle Run prediction
                break;
            case 6:
                // Handle Save/Load Neural Network
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

    private void handleVisualise() {
        actualUI.displayNeuralNetwork(app.getNeuralNetwork(),true);
        this.runMenu();
    }

}
