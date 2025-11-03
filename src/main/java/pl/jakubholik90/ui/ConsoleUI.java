package pl.jakubholik90.ui;

import pl.jakubholik90.domains.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUI {

    public static void showNeuralNetwork(NeuralNetwork neuralNetwork, Double[] inputData, boolean showEmpty) {
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


}
