package pl.jakubholik90.dto;


import pl.jakubholik90.domains.ActivationFunctionInterface;
import pl.jakubholik90.domains.ActivationFunctions;

public class NeuralNetworkConfig {

    private int[] structure;
    private int numberOfIterations;
    private double eta;
    private ActivationFunctionInterface outputLayerActivationFunction;
    private ActivationFunctionInterface hiddenLayerActivationFunction;

    public NeuralNetworkConfig() {
        // default config
        this.structure = new int[] {3,4,1};
        this.numberOfIterations = 100;
        this.eta = 0.01;
        this.outputLayerActivationFunction = ActivationFunctions::sigmoid;
        this.hiddenLayerActivationFunction = ActivationFunctions::leakyRelu;
    }

    public void setNumberOfInputs(int numberOfInputs) {
        this.structure[0] = numberOfInputs;
    }
    public void setNumberOfOutputs(int numberOfOutputs) {
        this.structure[this.structure.length - 1] = numberOfOutputs;
    }

    public void insertHiddenLayer(int position, int numberOfNeurons) {
        checkHiddenLayerPosition(position);
        int[] newStructure = new int[structure.length + 1];
        for (int i = 0, j = 0; i < newStructure.length; i++) {
            if (i == position) {
                newStructure[i] = numberOfNeurons;
            } else {
                newStructure[i] = structure[j];
                j++;
            }
        }
        this.structure = newStructure;
    }

    public void modifyHiddenLayer(int position, int numberOfNeurons) {
        checkHiddenLayerPosition(position);
        this.structure[position] = numberOfNeurons;
    }

    public void deleteHiddenLayer(int position) {
        checkHiddenLayerPosition(position);
        int[] newStructure = new int[structure.length - 1];
        for (int i = 0, j = 0; i < structure.length; i++) {
            if (i != position) {
                newStructure[j] = structure[i];
                j++;
            }
        }
        this.structure = newStructure;
    }

    private void checkHiddenLayerPosition(int position) {
        // position is 1-based index for hidden layers
        if (position < 1) {
            throw new IllegalArgumentException("Invalid position for hidden layer (too low)");
        } else if (position > this.structure.length - 1) {
            throw new IllegalArgumentException("Invalid position for hidden layer (too high)");
        }
    }


    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public void setEta(double eta) {
        this.eta = eta;
    }

    private ActivationFunctionInterface setLayerActivationFunction(String activationFunction) {
        if (checkActivationFunctionExists(activationFunction)) {
            ActivationFunctionsNames activationFunctionName = ActivationFunctionsNames.valueOf(activationFunction);
            switch (activationFunctionName) {
                case SIGMOID:
                    return ActivationFunctions::sigmoid;
                case RELU:
                    return ActivationFunctions::relu;
                case LEAKY_RELU:
                    return ActivationFunctions::leakyRelu;
                case DUMMY:
                    return ActivationFunctions::leakyRelu;
                default:
                    return ActivationFunctions::dummy;
            }
        } else {
            // here to be complete in the future: error exception handling
            return ActivationFunctions::dummy;
        }
    }

    public void setHiddenLayerActivationFunction(String activationFunction) {
        this.hiddenLayerActivationFunction = setLayerActivationFunction(activationFunction);
    }

    public void setOutputLayerActivationFunction(String activationFunction) {
        this.outputLayerActivationFunction = setLayerActivationFunction(activationFunction);
    }

    private boolean checkActivationFunctionExists(String functionName) {
        for (ActivationFunctionsNames afn : ActivationFunctionsNames.values()) {
            if (afn.toString().equals(functionName)) {
                return true;
            }
        }
        return false;
    }

    // getters

    public int[] getStructure() {
        return structure;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public double getEta() {
        return eta;
    }

    public ActivationFunctionInterface getOutputLayerActivationFunction() {
        return outputLayerActivationFunction;
    }

    public ActivationFunctionInterface getHiddenLayerActivationFunction() {
        return hiddenLayerActivationFunction;
    }
}
