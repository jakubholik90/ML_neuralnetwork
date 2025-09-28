package pl.jakubholik90;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//set of random static methods used for testing, displaying, etc
public class Utils {

    // displaying of List<Double[]>
    public static void displayArrayMatrix(List<Double[]> inputMatrix) {
        for (int i = 0; i < inputMatrix.size(); i++) {
            LinkedList<Double> returnLayer = new LinkedList<>();
            Collections.addAll(returnLayer, inputMatrix.get(i));
            System.out.println("layer" + i +":" + returnLayer);
        }
    }

    // displaying of List<Double[][]>
    public static void displayArrayMatrix2(List<Double[][]> inputMatrix) {
        for (int i = 0; i < inputMatrix.size(); i++) {
            LinkedList<String> returnLayer = new LinkedList<>();
            Collections.addAll(returnLayer, Arrays.deepToString(inputMatrix.get(i)));
            System.out.println("layer" + i +":" + returnLayer);
        }
    }


}
