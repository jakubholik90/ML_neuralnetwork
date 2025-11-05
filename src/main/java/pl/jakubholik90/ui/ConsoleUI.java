package pl.jakubholik90.ui;

import pl.jakubholik90.domains.NeuralNetwork;
import pl.jakubholik90.menus.MenuItem;
import pl.jakubholik90.menus.MenuTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements UI{

    private final Scanner scanner;
    private boolean isWindows;


    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
    }


    @Override
    public void clearScreen() {
        try {
            if (isWindows) {
                // Windows: Use ProcessBuilder to execute 'cls'
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix/Linux/Mac: Use ANSI escape codes
                System.out.print("\033[H\033[2J");
                System.out.flush();

                // Alternative: execute 'clear' command
                // new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("System not recognized for clearing the screen.");
        }
    }

    @Override
    public void displayNeuralNetwork(NeuralNetwork neuralNetwork, Double[] inputData, boolean showEmpty) {
        int[] structure = neuralNetwork.getStructure();
        List<Double[]> activationsMatrix = neuralNetwork.getActivationMatrix();
        List<String[]> activationsToShow = new ArrayList<>();
        for (int layer=0; layer < activationsMatrix.size(); layer++) {
            String[] layerToShow = new String[activationsMatrix.get(layer).length];
            if (showEmpty) {
                if (layer == 0) {
                    for (int node = 0; node < activationsMatrix.get(layer).length; node++) {
                        layerToShow[node] = "Inp";
                    }
                } else if (layer == activationsMatrix.size()-1) {
                    for (int node = 0; node < activationsMatrix.get(layer).length; node++) {
                        layerToShow[node] = "Out";
                    }
                } else {
                    for (int node = 0; node < activationsMatrix.get(layer).length; node++) {
                        layerToShow[node] = "Hi" + layer;
                    }
                }

            } else {
                for (int node = 0; node < activationsMatrix.get(layer).length; node++) {
                    layerToShow[node] = String.format("%.2f", activationsMatrix.get(layer)[node]);
                }
            }


            activationsToShow.add(layerToShow);
        }

        for (int layer = 0; layer < structure.length; layer++) {

            if (layer != 0) {
                ArrayList<String> midLinesList = new ArrayList<>(structure[layer]);
                for (int node = 0; node < structure[layer]; node++) {
                    if (node == 0) {
                        if (structure[layer] == 1) {
                            midLinesList.add("  |  ");
                        } else {
                            midLinesList.add("  |‾‾");
                        }
                    } else if (node == structure[layer]-1) {
                        midLinesList.add("‾‾|  ");
                    } else {
                        midLinesList.add("‾‾|‾‾");
                    }
                }
                String midLines = String.join("‾", midLinesList);
                System.out.println("  |");
                System.out.println(midLines);
            }

            ArrayList<String> layerToPresent = new ArrayList<>(structure[layer]);
            for (int node = 0; node < structure[layer]; node++) {
                layerToPresent.add("[" + activationsToShow.get(layer)[node] + "]");
            }
            String lineToPresent = String.join(" ", layerToPresent);
            System.out.println(lineToPresent);

            if (layer != structure.length-1) {
                ArrayList<String> botLinesList = new ArrayList<>(structure[layer]);
                for (int node = 0; node < structure[layer]; node++) {
                    if (node == 0) {
                        if (structure[layer] == 1) {
                            botLinesList.add("  |  ");
                        } else {
                            botLinesList.add("  |__");
                        }
                    } else if (node == structure[layer]-1) {
                        botLinesList.add("__|  ");
                    } else {
                        botLinesList.add("__|__");
                    }
                }
                String botLines = String.join("_", botLinesList);
                System.out.println(botLines);
            }
        }
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public int displayMenuAskChoice(MenuTable menuTable) {
        String title = menuTable.getTitle();
        displayMessage("--- " + title + " ---");
        if (!menuTable.getAdditionalMessage().isEmpty()) {;
            displayMessage(menuTable.getAdditionalMessage());
        }
        int menuSize = menuTable.getMenuSize();
        for (int id = 0; id < menuSize; id++) {
            MenuItem menuItemById = menuTable.getMenuItemById(id);
            String name = menuItemById.name();
            String description = menuItemById.description();
            String descriptionOptional;
            if (!description.isEmpty()) {
                descriptionOptional = " - " + description;
            } else {
                descriptionOptional = "";
            }
            String lineToShow = id + ": " + name + descriptionOptional;
            displayMessage(lineToShow);
        }
        displayMessage("Please enter your choice (use option number): ");
        int userChoice = Integer.valueOf(new Scanner(System.in).nextLine());
        return userChoice;
    }
}
