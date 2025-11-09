package pl.jakubholik90.menus;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

public class TrainNNMenu extends MenuAbstract {

    private MainMenu mainMenu;

    public TrainNNMenu(UI actualUI, App app) {
        super(actualUI, app);
    }

    @Override
    public MenuTable create() {
        MenuTable menuTable = new MenuTable("Train Neural Network Menu","");
        menuTable.addMenuItem(new MenuItem(1,"View actual data", "Visualise actual set of training data"));
        menuTable.addMenuItem(new MenuItem(2,"Manage", "Manage actual set of training data by hand"));
        menuTable.addMenuItem(new MenuItem(3,"Load", "Load set of training data from file"));
        menuTable.addMenuItem(new MenuItem(4,"Save", "Save actual set of training data to file"));
        menuTable.addMenuItem(new MenuItem(5,"Start Training", "Start training with actual set of training data"));
        menuTable.addMenuItem(new MenuItem(6,"View actual structure", "Visualise the structure (activations) of neural network"));
        menuTable.addMenuItem(new MenuItem(0,"Back to Main menu", ""));
        return menuTable;
    }

    @Override
    public void handleChoice(int userChoice) {
        switch (userChoice) {
            case 1:
                // Handle View actual data
                break;
            case 2:
                // Handle Manage
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
}
