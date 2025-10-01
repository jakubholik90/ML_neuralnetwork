package pl.jakubholik90;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //test activation functions
        int[] structure = new int[] {2,4,2};
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
        Double[][] testInputMatrix = new Double[][] {{-0.5,4.0},{3.0,-5.0},{1.0,-2.0}};
//
//        Double[] doubles = NumPyLike.matrixVectorMultiply(testInputMatrix, testInputVector);
//        System.out.println("\nresult of multiplication:" + Arrays.toString(doubles));

        // test feed forward
        Double[] testInput = {4.0,4.0};
        Double[] nn1Step1Result = nn1.step1FeedForward(testInput);
        System.out.println(Arrays.toString(nn1Step1Result));
        System.out.println("---");
        nn2.step0Build();
        Double[] nn2Step1Result = nn2.step1FeedForward(testInput);
        System.out.println(Arrays.toString(nn2Step1Result));

        // test - matrix transpose
        List<Double[][]> testList = new ArrayList<>();
        testList.add(testInputMatrix);
        System.out.println("testList:");
        Utils.displayArrayMatrix2(testList);
        Double[][] transpose = NumPyLike.transposeMatrix(testInputMatrix);
        List<Double[][]> testList2 = new ArrayList<>();
        testList2.add(transpose);
        System.out.println("testList2:");
        Utils.displayArrayMatrix2(testList2);


        // test backpropagation
        ArrayList<TrainingDataRecord> trainingDataList = new ArrayList<>(3);
        TrainingDataRecord record1 = new TrainingDataRecord(new Double[]{1.0, 1.0,},new Double[]{2.0,2.0});
        TrainingDataRecord record2 = new TrainingDataRecord(new Double[]{2.0, 2.0,},new Double[]{4.0,4.0});
        TrainingDataRecord record3 = new TrainingDataRecord(new Double[]{3.0, 3.0,},new Double[]{6.0,6.0});
        trainingDataList.add(record1);
        trainingDataList.add(record2);
        trainingDataList.add(record3);


//            ArrayList<Double[]> partialDeltasList = new ArrayList<>(structure.length);
//            System.out.println("structure.length:" + structure.length);
//            System.out.println("partialDeltasList.size:" + partialDeltasList.size()); // sprawdzic jak zrobic pusta array lsite z n elementami!!!
//
//            // for loop after each layer
//            for (int i = 0; i < nn1.activationsMatrix.size(); i++) {
//                partialDeltasList.set(i,NumPyLike.zeros(nn1.weightedSumsMatrix.get(i).length));
//            }

        nn1.step2BackPropagation(trainingDataList);



    }
}