package pl.jakubholik90.controllers;

import com.google.gson.Gson;
import pl.jakubholik90.neuralnetwork.NeuralNetwork;
import pl.jakubholik90.dto.NeuralNetworkConfig;

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

}
