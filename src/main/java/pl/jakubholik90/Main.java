package pl.jakubholik90;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        //test
        int[] structure = new int[] {1,2};
        NeuralNetwork nn1 = new NeuralNetwork(structure);
        double v1 = nn1.runOutputActivationFunction(10, false);
        NeuralNetwork nn2 = new NeuralNetwork(structure,1,1.0,ActivationFunctions::dummy,ActivationFunctions::dummy);
        double v2 = nn2.runOutputActivationFunction(10, false);

        System.out.println(v1);
        System.out.println(v2);

        System.out.println(Arrays.deepToString(NumPyLike.ones(5)));
    }
}