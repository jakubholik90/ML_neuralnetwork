package pl.jakubholik90.neuralnetwork;


import pl.jakubholik90.dto.ActivationFunctionsEnum;

public class NeuralNetworkConfig {

    private int[] structure;
    private int numberOfIterations;
    private double eta;
    private ActivationFunctionInterface outputLayerActivationFunction;
    private ActivationFunctionInterface hiddenLayerActivationFunction;

    public NeuralNetworkConfig() {
        // default config
        this.structure = new int[] {3,4,1};
        this.numberOfIterations = 2000;
        this.eta = 0.3;
        this.outputLayerActivationFunction = ActivationFunctionsEnum.LEAKY_RELU.function;
        this.hiddenLayerActivationFunction = ActivationFunctionsEnum.LEAKY_RELU.function;
    }

    public NeuralNetworkConfig(NeuralNetworkConfig other) { // constructor for copying from other config
        this.structure = other.structure.clone(); // Clone the array
        this.numberOfIterations = other.numberOfIterations;
        this.eta = other.eta;
        this.outputLayerActivationFunction = other.outputLayerActivationFunction;
        this.hiddenLayerActivationFunction = other.hiddenLayerActivationFunction;
    }

    public void setStructure(int[] structure) {
        this.structure = structure;
    }

    public void setNumberOfInputs(int numberOfInputs) {
        this.structure[0] = numberOfInputs;
    }
    public void setNumberOfOutputs(int numberOfOutputs) {
        this.structure[this.structure.length - 1] = numberOfOutputs;
    }

    public void insertHiddenLayer(int position, int numberOfNeurons) {
        if (position < 1) {
            throw new IllegalArgumentException("Invalid position for hidden layer (too low)");
        } else if (position > this.structure.length-1) {
            throw new IllegalArgumentException("Invalid position for hidden layer (too high)");
        }
        int[] newStructure = new int[structure.length + 1];
        int oldNummeration = 0;

        for (int newNummeration = 0; newNummeration < newStructure.length; newNummeration++) {
            if (newNummeration == position) {
                newStructure[newNummeration] = numberOfNeurons;
            } else {
                newStructure[newNummeration] = structure[oldNummeration];
                oldNummeration++;
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
        } else if (position > this.structure.length - 2) {
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
            ActivationFunctionsEnum activationFunctionName = ActivationFunctionsEnum.valueOf(activationFunction);
            switch (activationFunctionName) {
                case SIGMOID:
                    return ActivationFunctionsEnum.SIGMOID.function;
                case RELU:
                    return ActivationFunctionsEnum.RELU.function;
                case LEAKY_RELU:
                    return ActivationFunctionsEnum.LEAKY_RELU.function;
                case DUMMY:
                    return ActivationFunctionsEnum.DUMMY.function;
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
        for (ActivationFunctionsEnum afn : ActivationFunctionsEnum.values()) {
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

    public String getNameActivationFunction(ActivationFunctionInterface activationFunction) {
        if (activationFunction == ActivationFunctionsEnum.DUMMY.function) {
            return ActivationFunctionsEnum.DUMMY.name();
        } else if (activationFunction == ActivationFunctionsEnum.SIGMOID.function) {
            return ActivationFunctionsEnum.SIGMOID.name();
        } else if (activationFunction == ActivationFunctionsEnum.RELU.function) {
            return ActivationFunctionsEnum.RELU.name();
        } else if (activationFunction == ActivationFunctionsEnum.LEAKY_RELU.function) {
            return ActivationFunctionsEnum.LEAKY_RELU.name();
        } else {
            return "UNKNOWN_ACTIVATION_FUNCTION";
        } // to avoid warning about unused parameter

    }
}
