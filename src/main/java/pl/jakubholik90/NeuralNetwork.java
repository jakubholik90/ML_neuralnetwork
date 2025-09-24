package pl.jakubholik90;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class NeuralNetwork {

    private final int[] structure;
    private final int numberOfIterations;
    private final double eta;
    private final ActivationFunctionInterface outputLayerActivationFunction;
    private final ActivationFunctionInterface hiddenLayerActivationFunction;
    private List<Double[][]> activationsMatrix;
    private List<Double[][]> weightedSumsMatrix;
    private List<Double[][]> weights;

    public NeuralNetwork(int[] structure) {
        // simple constructor, passing only structure
        this.structure = structure;
        this.numberOfIterations = 1000;
        this.eta = 0.01;
        this.outputLayerActivationFunction = ActivationFunctions::leakyRelu;
        this.hiddenLayerActivationFunction = ActivationFunctions::sigmoid;
    }

    public NeuralNetwork(int[] structure, int numberOfIterations, double eta, ActivationFunctionInterface outputLayerActivationFunction, ActivationFunctionInterface hiddenLayerActivationFunction) {
        // advanced constructor
        this.structure = structure;
        this.numberOfIterations = numberOfIterations;
        this.eta = eta;
        this.outputLayerActivationFunction = outputLayerActivationFunction;
        this.hiddenLayerActivationFunction = hiddenLayerActivationFunction;
    }

    // main method to build all necessary subcomponents
    public void build() {
        this.createLayers();
        this.createWeights();
    }

    // create necessary components of all layers
    private void createLayers() {
        // for loop for each structure layer
        boolean isOutputLayer;
        List<Double[][]> activationsMatrixLList = new LinkedList<>();
        List<Double[][]> weightedSumsMatrixLList = new LinkedList<>();
        for (int i = 0; i < this.structure.length; i++) {
            isOutputLayer = (i == (this.structure.length-1)); //boolean for checking if this is the last layer
            int numberOfActivations; //number of activations in each layer (for hidden layers +1 because of bias activation)
            if (isOutputLayer) {
                numberOfActivations = this.structure[i];
            } else {
                numberOfActivations = this.structure[i] + 1;
            }
            int numberOfWeightedSums = this.structure[i]; //number of weighted sums in each layer
            Double[][] weightedSumsInLayer = NumPyLike.zeros(numberOfWeightedSums); //vector for weighted sums (initial values)
            Double[][] activationsInLayer = NumPyLike.ones(numberOfActivations); //vector for activations (initial values)
            weightedSumsMatrixLList.add(weightedSumsInLayer);
            activationsMatrixLList.add(activationsInLayer);
        }
        this.weightedSumsMatrix = new ArrayList<>(weightedSumsMatrixLList);
        this.activationsMatrix = new ArrayList<>(activationsMatrixLList);
    }

    // create necessary components of all weights
    private void createWeights() {
        LinkedList<Double[][]> weightsMatrixLList = new LinkedList<>();
        // for loop for each activation layer
        for (int i = 0; i < this.activationsMatrix.size(); i++) {
            if (i==0) {
                weightsMatrixLList.add(new Double[0][0]); // in layer 0 there ist empty weights matrix, we want nummeration to start with 1
            } else {

                Double[][] layerWeights = NumPyLike.random(this.weightedSumsMatrix.get(i).)

                        // tutaj skonczyc, wyciagnac jakos te rozmiary tabeli

            }
        }

    }

    //check if needed
    public double runOutputActivationFunction(double z, boolean calculateDerivative) {
        return this.outputLayerActivationFunction.activationFunction(z,calculateDerivative);
    }

}
