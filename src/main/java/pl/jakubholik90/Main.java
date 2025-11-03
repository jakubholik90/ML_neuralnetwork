package pl.jakubholik90;

import pl.jakubholik90.domains.ActivationFunctions;
import pl.jakubholik90.domains.NeuralNetwork;
import pl.jakubholik90.domains.TrainingDataRecord;
import pl.jakubholik90.ui.ConsoleUI;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        int[] structure = new int[] {3,4,2,1};
        NeuralNetwork nn1 = new NeuralNetwork(structure,3000,0.01, ActivationFunctions::dummy,ActivationFunctions::sigmoid);

        nn1.step0Build();


        ArrayList<TrainingDataRecord> trainingDataList = new ArrayList<>(8);
        TrainingDataRecord record0 = new TrainingDataRecord(new Double[]{0.0,0.0,0.0},new Double[]{0.0});
        TrainingDataRecord record1 = new TrainingDataRecord(new Double[]{0.0,0.0,1.0},new Double[]{1.0});
        TrainingDataRecord record2 = new TrainingDataRecord(new Double[]{0.0,1.0,0.0},new Double[]{2.0});
        TrainingDataRecord record3 = new TrainingDataRecord(new Double[]{0.0,1.0,1.0},new Double[]{3.0});
        TrainingDataRecord record4 = new TrainingDataRecord(new Double[]{1.0,0.0,0.0},new Double[]{4.0});
        TrainingDataRecord record5 = new TrainingDataRecord(new Double[]{1.0,0.0,1.0},new Double[]{5.0});
        TrainingDataRecord record6 = new TrainingDataRecord(new Double[]{1.0,1.0,0.0},new Double[]{6.0});
        TrainingDataRecord record7 = new TrainingDataRecord(new Double[]{1.0,1.0,1.0},new Double[]{7.0});
        trainingDataList.add(record0);
        trainingDataList.add(record1);
        trainingDataList.add(record2);
        trainingDataList.add(record3);
        trainingDataList.add(record4);
        trainingDataList.add(record5);
        trainingDataList.add(record6);
        trainingDataList.add(record7);

        Double[] testInput = {0.0,1.0,0.0};
        Double[] calculatedValueBefore = nn1.step1FeedForward(testInput);
        // System.out.println("calculated value before training: " + Arrays.toString(calculatedValueBefore));

        nn1.step2BackPropagation(trainingDataList);

        Double[] calculatedValueAfter = nn1.step1FeedForward(testInput);

        // System.out.println("calculated value after training: " + Arrays.toString(calculatedValueAfter));

        ConsoleUI.showNeuralNetwork(nn1,testInput,false );

    }
}