package pl.jakubholik90.domains;

import pl.jakubholik90.WrongInputSizeException;
import pl.jakubholik90.others.NumPyLike;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NeuralNetwork {

    private final int[] structure;
    private final int numberOfIterations;
    private final double eta;
    private final ActivationFunctionInterface outputLayerActivationFunction;
    private final ActivationFunctionInterface hiddenLayerActivationFunction;
    public List<Double[]> activationsMatrix;
    public List<Double[]> weightedSumsMatrix;
    public List<Double[][]> weightsMatrix;
    public List<Double> errorProgression;

    public NeuralNetwork(int[] structure) {
        // simple constructor, passing only structure
        this.structure = structure;
        this.numberOfIterations = 1000;
        this.eta = 0.01;
        this.outputLayerActivationFunction = ActivationFunctions::sigmoid;
        this.hiddenLayerActivationFunction = ActivationFunctions::leakyRelu;
        this.errorProgression = new ArrayList<>(this.numberOfIterations);
    }

    public NeuralNetwork(int[] structure, int numberOfIterations, double eta, ActivationFunctionInterface outputLayerActivationFunction, ActivationFunctionInterface hiddenLayerActivationFunction) {
        // advanced constructor
        this.structure = structure;
        this.numberOfIterations = numberOfIterations;
        this.eta = eta;
        this.outputLayerActivationFunction = outputLayerActivationFunction;
        this.hiddenLayerActivationFunction = hiddenLayerActivationFunction;
        this.errorProgression = new ArrayList<>(this.numberOfIterations);
    }

    //getters
    public int[] getStructure() {
        return structure;
    }
    public List<Double[]> getActivationMatrix() {
        return activationsMatrix;
    }

    // STEP 0: main method to build all necessary subcomponents
    public void step0Build() {
        this.createLayers();
        this.createWeights();
    }

    // create necessary components of all layers
    private void createLayers() {
        // for loop for each structure layer
        boolean isOutputLayer;
        List<Double[]> activationsMatrixLList = new LinkedList<>();
        List<Double[]> weightedSumsMatrixLList = new LinkedList<>();
        for (int i = 0; i < this.structure.length; i++) {
            isOutputLayer = (i == (this.structure.length-1)); //boolean for checking if this is the last layer
            int numberOfActivations; //number of activations in each layer (for hidden layers +1 because of bias activation)
            if (isOutputLayer) {
                numberOfActivations = this.structure[i];
            } else {
                numberOfActivations = this.structure[i] + 1;
            }
            int numberOfWeightedSums = this.structure[i]; //number of weighted sums in each layer
            Double[] weightedSumsInLayer = NumPyLike.zeros(numberOfWeightedSums); //vector for weighted sums (initial values)
            Double[] activationsInLayer = NumPyLike.ones(numberOfActivations); //vector for activations (initial values)
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
                Double[][] layerWeights = NumPyLike.randomMinus1to1(this.weightedSumsMatrix.get(i).length,this.activationsMatrix.get(i-1).length);
                weightsMatrixLList.add(layerWeights);
            }
        }
        this.weightsMatrix = new ArrayList<>(weightsMatrixLList);

    }
    // STEP1: feedforward
    public Double[] step1FeedForward(Double[] inputLayer) {
        //checking if input has correct size
        if (inputLayer.length != (this.activationsMatrix.get(0).length)-1) {
            throw new WrongInputSizeException("Input size does not match structure size. Check your input data.");
        }

        // applying input to first layer of activation matrix (index i=0 is for bias with value 1, therefore for loop starting with i=1)
        for (int i = 1; i < this.activationsMatrix.get(0).length; i++) {
            this.activationsMatrix.get(0)[i]=inputLayer[i-1]; // inserting input i-1 into first layer(index 0) of activations, index i
        }

        // loop after each hidden layer and output layer
        for (int i = 1; i < this.activationsMatrix.size(); i++) {
            Double[] weightedSumsLayerToReplace= NumPyLike.matrixTimesVector(this.weightsMatrix.get(i), this.activationsMatrix.get(i - 1));// vector of acvivation layer (for example i=0) times weights matrix (for example i=1)
        this.weightedSumsMatrix.set(i,weightedSumsLayerToReplace); // inserting weighted sums layer into weighted matrix

            // running activation function in hidden layers and output layer
            if (i == (this.activationsMatrix.size()-1)) {
                // is in output layer
                for (int j = 0; j < this.weightedSumsMatrix.get(i).length; j++) {
                    this.activationsMatrix.get(i)[j] = this.runOutputActivationFunction(weightedSumsMatrix.get(i)[j], false);
                }
            } else {
                // is in hiddenlayer layer
                for (int j = 0; j < this.weightedSumsMatrix.get(i).length; j++) { // taking activationMatrix[j+1] due to bias
                    this.activationsMatrix.get(i)[j+1] = this.runHiddenActivationFunction(weightedSumsMatrix.get(i)[j], false);
                }
            }
        }

        Double[] returnArray = this.activationsMatrix.getLast();
        return returnArray;
    }

    // step 2 BACK PROPAGATION
    public void step2BackPropagation(List<TrainingDataRecord> trainingDataRecordList) {
        //for loop after max. iterations number
        for (int iteration = 0; iteration < this.numberOfIterations; iteration++) {
            // list of partial derivatives in each layer
            ArrayList<Double[]> partialDeltasList = new ArrayList<>(this.structure.length);
            for (int i = 0; i < this.structure.length; i++) {
                partialDeltasList.add(i, null);
            }

            // for loop after each layer
            for (int i = 0; i < this.activationsMatrix.size(); i++) {
                partialDeltasList.set(i, NumPyLike.zeros(this.weightedSumsMatrix.get(i).length));
            }

            // list of derivatives (differences??) from weights in each layer
            ArrayList<Double[][]> deltaWlist = new ArrayList<>(this.structure.length);
            for (int i = 0; i < this.structure.length; i++) {
                deltaWlist.add(i, null);
            }

            // list of gradient accumulators for this iteration
            for (int layer = 0; layer < this.structure.length; layer++) {
                if (layer == 0) {
                    deltaWlist.set(layer, new Double[0][0]); // empty Array in input layer (placeholder, weights are numbered starting with i=1=
                } else {
                    for (int layerElement = 0; layerElement < this.weightsMatrix.get(layer).length; layerElement++) {
                        deltaWlist.set(layer, NumPyLike.zeros2D(
                                this.weightsMatrix.get(layer).length, // number of rows (number of neurons in previous layer + 1 (bias))
                                this.weightsMatrix.get(layer)[layerElement].length // number of columns (number of neurons in current layer + 1 (bias))
                        ));
                    }
                }
            }


            // Double[] calculatedPrediction = new Double[trainingDataRecordList.size()]; - checking lower line
            Double[] difference = new Double[trainingDataRecordList.getFirst().trainingOutput().length]; // difference between calculated preduction and given output in training data
            Double[] outputLayerDerivative = new Double[this.activationsMatrix.getLast().length];
            double error = 0.0;

            //for loop after each training data record
            for (int trainingDataNumber = 0; trainingDataNumber < trainingDataRecordList.size(); trainingDataNumber++) {
                TrainingDataRecord dataRecord = trainingDataRecordList.get(trainingDataNumber);

                // checking if data record has proper number of expected outputs
                if (dataRecord.trainingOutput().length != this.activationsMatrix.getLast().length) {
                    throw new WrongInputSizeException("Output size of given training data does not match size of neural network structure (output layer)");
                }

                // feed forward pass
                Double[] calculatedPrediction = this.step1FeedForward(dataRecord.trainingInput());

                // loop after each element in output layer
                for (int outputLayerElement = 0; outputLayerElement < dataRecord.trainingOutput().length; outputLayerElement++) {
                    difference[outputLayerElement] = calculatedPrediction[outputLayerElement] - dataRecord.trainingOutput()[outputLayerElement];
                    error += Math.pow(difference[outputLayerElement], 2);
                }

                // calculate deltas for output layer
                //for loop after each output element
                for (int layerElement = 0; layerElement < this.activationsMatrix.getLast().length; layerElement++) {
                    // partial delta in output layer = output layer derivative * output activation function ( weighted sum)
                    outputLayerDerivative[layerElement] = 2 * difference[layerElement];
                    partialDeltasList.get(this.activationsMatrix.size() - 1)[layerElement] = outputLayerDerivative[layerElement] * this.runOutputActivationFunction(this.weightedSumsMatrix.getLast()[layerElement], true);
                }

                // calculating deltas for hidden layers
                //for loop backwards after each hidden layer
                for (int layer = this.activationsMatrix.size() - 2; layer > 0; layer--) {
                    //for loop after each layer element
                    for (int neuron = 0; neuron < this.weightedSumsMatrix.get(layer).length; neuron++) {
                        double sum = 0.0;

                        // sum over next layer
                        for (int nextNeuron = 0; nextNeuron < partialDeltasList.get(layer + 1).length; nextNeuron++) {
                            //weight from current neuron to next layer neuron
                            //add 1 to neuron index if bias is present in current layer
                            int weightIndex = neuron + 1;
                            sum += this.weightsMatrix.get(layer + 1)[nextNeuron][weightIndex] * partialDeltasList.get(layer + 1)[nextNeuron];
                        }
                        partialDeltasList.get(layer)[neuron] = sum * this.runHiddenActivationFunction(this.weightedSumsMatrix.get(layer)[neuron], true);
                    }
                }

                // accumulate weight gradients for this data record
                for (int layer = 1; layer < this.structure.length; layer++) {
                    for (int i = 0; i < this.weightsMatrix.get(layer).length; i++) {
                        for (int j = 0; j < this.weightsMatrix.get(layer)[i].length; j++) {
                            double gradient = partialDeltasList.get(layer)[i] * this.activationsMatrix.get(layer - 1)[j];
                            deltaWlist.get(layer)[i][j] += gradient; // accumulating weight gradient
                        }
                    }
                }
            }

            // update weights using averaged gradients
            for (int layer = 1; layer < this.structure.length; layer++) {
                for (int i = 0; i < this.weightsMatrix.get(layer).length; i++) {
                    for (int j = 0; j < this.weightsMatrix.get(layer)[i].length; j++) {
                        double averageGradient = (deltaWlist.get(layer)[i][j] / trainingDataRecordList.size()) * (-1) * this.eta;
                        this.weightsMatrix.get(layer)[i][j] += averageGradient; // adjusting weight with delta W
                    }
                }
            }

            this.errorProgression.add(error); // adding current error to progression
            // System.out.println("iteration:" + iteration + ", difference:" + Arrays.toString(difference) + ", error:" + error);
        }

    }

    // run activation methods
    public double runOutputActivationFunction(double z, boolean calculateDerivative) {
        return this.outputLayerActivationFunction.activationFunction(z,calculateDerivative);
    }

    public double runHiddenActivationFunction(double z, boolean calculateDerivative) {
        return this.hiddenLayerActivationFunction.activationFunction(z,calculateDerivative);
    }

}
