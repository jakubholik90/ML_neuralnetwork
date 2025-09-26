package pl.jakubholik90;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        //test activation functions
        int[] structure = new int[] {4,5,2};
        NeuralNetwork nn1 = new NeuralNetwork(structure);
//        double v1 = nn1.runOutputActivationFunction(10, false);
        NeuralNetwork nn2 = new NeuralNetwork(structure,1,1.0,ActivationFunctions::dummy,ActivationFunctions::dummy);
//        double v2 = nn2.runOutputActivationFunction(10, false);
//
//        System.out.println(v1);
//        System.out.println(v2);

        //test numpylike.ones
//        System.out.println(Arrays.deepToString(NumPyLike.ones(5)));
//        System.out.println(new Random().nextDouble(1));
//        System.out.println(new Random().nextDouble(1));
//        System.out.println(new Random().nextDouble(1));
//        System.out.println(new Random().nextDouble(1));
//        System.out.println(new Random().nextDouble(1));

        //test neuralnetwork.build()
        nn1.step0Build();
        System.out.println("\nweightsMatrix:");
        Utils.displayArrayMatrix2(nn1.weightsMatrix);
        System.out.println("\nweightedSumsMatrix:");
        Utils.displayArrayMatrix(nn1.weightedSumsMatrix);
        System.out.println("\nactivationsMatrix:");
        Utils.displayArrayMatrix(nn1.activationsMatrix);

        // test exception throw
//        Double[] testInput = {2.1,2.2,3.0};
//        nn1.step1FeedForward(testInput);

        // test matrix multiplication
//        Double[] testInputVector = new Double[] {2.0,-6.0};
//        Double[][] testInputMatrix = new Double[][] {{-0.5,4.0},{3.0,-5.0},{1.0,-2.0}};
//
//        Double[] doubles = NumPyLike.matrixVectorMultiply(testInputMatrix, testInputVector);
//        System.out.println("\nresult of multiplication:" + Arrays.toString(doubles));

        // test feed forward
        Double[] testInput = {1.1,2.1,2.2,3.0};
        Double[] nn1Step1Result = nn1.step1FeedForward(testInput);
        System.out.println(Arrays.toString(nn1Step1Result));
        System.out.println("---");
        nn2.step0Build();
        Double[] nn2Step1Result = nn2.step1FeedForward(testInput);
        System.out.println(Arrays.toString(nn2Step1Result));


    }
}