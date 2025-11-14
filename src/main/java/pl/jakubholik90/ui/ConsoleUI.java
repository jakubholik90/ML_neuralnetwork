package pl.jakubholik90.ui;

import pl.jakubholik90.neuralnetwork.NeuralNetwork;
import pl.jakubholik90.menus.MenuItem;
import pl.jakubholik90.menus.MenuTable;

import java.util.ArrayList;
import java.util.HashMap;
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
    public void displayNeuralNetwork(NeuralNetwork neuralNetwork, boolean showEmpty) {
        int[] structure = neuralNetwork.getStructure();
        List<Double[]> activationsMatrix = neuralNetwork.getActivationMatrix();
        List<String[]> activationsToShow = new ArrayList<>();
        for (int layer=0; layer < activationsMatrix.size(); layer++) {
            String[] layerToShow = new String[activationsMatrix.get(layer).length];
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
                    if (showEmpty) {
                        layerToShow[node] = "Hi" + layer;
                    } else {
                        layerToShow[node] = String.format("%.2f", activationsMatrix.get(layer)[node]);
                    }
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
            displayMessage("( " + menuTable.getAdditionalMessage() + " )");
        }
        int menuSize = menuTable.getMenuSize();
        for (int id = 1; id < menuSize; id++) {
            displayMenuItem(menuTable, id);
        }
        if (menuTable.getMenuItemById(0) != null) {
            displayMenuItem(menuTable,0);
        }
        displayMessage("Please enter your choice (use option number): ");
        int userChoice = Integer.valueOf(getUserInput());
        return userChoice;
    }

    @Override
    public String getUserInput() {
        String returnString = scanner.nextLine();
        return returnString;
    }

    private void displayMenuItem(MenuTable menuTable, int id) {
        MenuItem menuItem = menuTable.getMenuItemById(id);
        String name = menuItem.name();
        String description = menuItem.description();
        String descriptionOptional;
        if (!description.isEmpty()) {
            descriptionOptional = " - " + description;
        } else {
            descriptionOptional = "";
        }
        String lineToShow = id + ": " + name + descriptionOptional;
        displayMessage(lineToShow);
    }

    @Override
    public void displayPlot(Double[] xValues, Double[] yValues, String title, String xLabel, String yLabel) {
        Integer[] xValuesInt = new Integer[xValues.length];
        HashMap<Integer, Double> plotMap = new HashMap<>();
        Double[] yValuesAdjusted = new Double[xValues.length];
        displayMessage("--- " + title + " ---");
        for (int i = 0; i < xValuesInt.length; i++) {
            xValuesInt[i] = xValues[i].intValue();
            if (i < yValues.length) {
                yValuesAdjusted[i] = yValues[i];
            } else {
                yValuesAdjusted[i] = 0.0;
            }
            plotMap.put(xValuesInt[i], yValues[i]);
            displayMessage(xLabel + ":" + xValuesInt[i] + ", " + yLabel + ":" + yValuesAdjusted[i]);
        }

        displayMessage("-------------------");
        plotPoints(xValues, yValuesAdjusted);

    }

    private void plotPoints(Double[] xValues, Double[] yValues) {
        // plot size
        int height = 10;
        int width = 50;

        // plot max min range
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        for (int i = 0; i < xValues.length; i++) {
            minY = Math.min(minY, yValues[i]);
            maxY = Math.max(maxY, yValues[i]);
            minX = Math.min(minX, xValues[i]);
            maxX = Math.max(maxX, xValues[i]);
        }

        char[][] grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = '.';
            }
        }

        for (int i = 0; i < xValues.length; i++) {
            int x = (int) ((xValues[i] - minX) / (maxX - minX) * (width - 1));
            int y = (int) ((yValues[i] - minY) / (maxY - minY) * (height - 1));
            y = height - 1 - y;
            if (x >= 0 && x < width && y >= 0 && y < height) {
                grid[y][x] = '*';
            }
        }

        // plotting Y axis
        for (int i = 0; i < height; i++) {
            double yVal = maxY - (i * (maxY - minY) / (height - 1));
            System.out.printf("%8.2f | ", yVal);
            System.out.println(grid[i]);
        }

        // plotting X axis
        System.out.println("         " + "-".repeat(width));
        System.out.printf("%9.2f%" + (width - 8) + ".2f%n", minX, maxX);
    }
}
