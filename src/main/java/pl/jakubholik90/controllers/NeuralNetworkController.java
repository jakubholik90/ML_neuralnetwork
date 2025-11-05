package pl.jakubholik90.controllers;

import pl.jakubholik90.domains.NeuralNetwork;
import pl.jakubholik90.dto.NeuralNetworkConfig;

public class NeuralNetworkController {

    public NeuralNetworkController() {
    }

    public NeuralNetwork createNeuralNetworkStandardConfig() {
        NeuralNetworkConfig standardConfig = new NeuralNetworkConfig(); // create config object with standard settings


        NeuralNetwork neuralNetwork = new NeuralNetwork(
                standardConfig.getStructure(),
                standardConfig.getNumberOfIterations(),
                standardConfig.getEta(),
                standardConfig.getOutputLayerActivationFunction(),
                standardConfig.getHiddenLayerActivationFunction());

        return neuralNetwork;
    }

}
