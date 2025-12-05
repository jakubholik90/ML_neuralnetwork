package pl.jakubholik90.dto;

import java.util.List;

public record NeuralNetworkSnapshotRecord(
        String name,
        int[] structure,
        int numberOfIterations,
        double eta,
        String outputLayerActivationFunction,
        String hiddenLayerActivationFunction,
        List<Double[]> activationsMatrix,
        List<Double[]> weightedSumsMatrix,
        List<Double[][]> weightsMatrix,
        String trainingDataSetName,
        String userComment,
        String timeStamp
) {
}
