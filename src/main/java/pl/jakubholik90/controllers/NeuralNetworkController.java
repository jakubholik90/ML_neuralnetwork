package pl.jakubholik90.controllers;

import pl.jakubholik90.dto.NeuralNetworkSnapshotRecord;
import pl.jakubholik90.neuralnetwork.NeuralNetwork;
import pl.jakubholik90.neuralnetwork.NeuralNetworkConfig;

public class NeuralNetworkController {

    public NeuralNetworkController() {
    }

    public NeuralNetwork createNeuralNetworkFromConfig(NeuralNetworkConfig config) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(
                config.getStructure(),
                config.getNumberOfIterations(),
                config.getEta(),
                config.getOutputLayerActivationFunction(),
                config.getHiddenLayerActivationFunction());

        return neuralNetwork;
    }

    public NeuralNetwork restoreNeuralNetworkFromSnapshot(NeuralNetworkSnapshotRecord importSnapshot) {
        NeuralNetworkConfig config = new NeuralNetworkConfig();
        config.setStructure(importSnapshot.structure());
        config.setNumberOfIterations(importSnapshot.numberOfIterations());
        config.setEta(importSnapshot.eta());
        config.setOutputLayerActivationFunction(importSnapshot.outputLayerActivationFunction());
        config.setHiddenLayerActivationFunction(importSnapshot.hiddenLayerActivationFunction());

        NeuralNetwork restoredNeuralNetwork = createNeuralNetworkFromConfig(config);

        restoredNeuralNetwork.step0Build();

        restoredNeuralNetwork.setWeightsMatrix(importSnapshot.weightsMatrix());
        restoredNeuralNetwork.setWeightedSumsMatrix(importSnapshot.weightedSumsMatrix());
        restoredNeuralNetwork.setActivationsMatrix(importSnapshot.activationsMatrix());

        return restoredNeuralNetwork;



    }

}
