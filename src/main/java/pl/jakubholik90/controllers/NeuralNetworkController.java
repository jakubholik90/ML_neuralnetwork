package pl.jakubholik90.controllers;

import pl.jakubholik90.neuralnetwork.NeuralNetwork;
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

    public NeuralNetwork createNeuralNetworkFromConfig(NeuralNetworkConfig config) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(
                config.getStructure(),
                config.getNumberOfIterations(),
                config.getEta(),
                config.getOutputLayerActivationFunction(),
                config.getHiddenLayerActivationFunction());

        return neuralNetwork;
    }

}
