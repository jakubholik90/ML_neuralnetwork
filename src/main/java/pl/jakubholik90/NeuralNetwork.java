package pl.jakubholik90;

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
        this.outputLayerActivationFunction = ActivationFunctions::leakyRelu;
        this.hiddenLayerActivationFunction = ActivationFunctions::sigmoid;
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


                // tutaj zmienione random na jedynki dla testow
                // Double[][] layerWeights = NumPyLike.randomMinus1to1(this.weightedSumsMatrix.get(i).length,this.activationsMatrix.get(i-1).length);



                Double[][] layerWeights = NumPyLike.ones2D(this.weightedSumsMatrix.get(i).length,this.activationsMatrix.get(i-1).length);
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
                for (int j = 1; j < this.weightedSumsMatrix.get(i).length; j++) { // sarting foorm i=1 due to bias
                    this.activationsMatrix.get(i)[j] = this.runHiddenActivationFunction(weightedSumsMatrix.get(i)[j], false);
                }
            }
        }

        Double[] returnArray = this.activationsMatrix.getLast();
        return returnArray;
    }

    // step 2 BACK PROPAGATION

    public void step2BackPropagation(List<TrainingDataRecord> trainingDataRecordList) {
        // list of partial derivatives in each layer
        ArrayList<Double[]> partialDeltasList = new ArrayList<>(this.structure.length);
        for (int i = 0; i < this.structure.length; i++) {
            partialDeltasList.add(i,null);
        }

        // for loop after each layer
        for (int i = 0; i < this.activationsMatrix.size(); i++) {
            partialDeltasList.set(i,NumPyLike.zeros(this.weightedSumsMatrix.get(i).length));
        }

        // list of derivatives (differences??) from weights in each layer
        ArrayList<Double[][]> deltaWlist = new ArrayList<>(this.structure.length);
        for (int i = 0; i < this.structure.length; i++) {
            deltaWlist.add(i,null);
        }

        for (int layer = 0; layer < this.structure.length; layer++) {
            if (layer == 0) {
                deltaWlist.set(layer,new Double[0][0]); // empty Array in input layer (placeholder, weights are numbered starting with i=1=
            } else {
                for (int layerElement = 0; layerElement < this.weightsMatrix.get(layer).length; layerElement++) {
                    deltaWlist.set(layer,NumPyLike.zeros2D(
                            this.weightsMatrix.get(layer).length, // number of rows (number of neurons in previous layer + 1 (bias))
                            this.weightsMatrix.get(layer)[layerElement].length // number of columns (number of neurons in current layer + 1 (bias))
                    ));
                }

            }

        }

        //for loop after max. iterations number
        for (int iteration = 0; iteration < this.numberOfIterations; iteration++) {
            // Double[] calculatedPrediction = new Double[trainingDataRecordList.size()]; - checking lower line
            Double[] calculatedPrediction = new Double[trainingDataRecordList.getFirst().trainingOutput().length];
            Double[] difference = new Double[trainingDataRecordList.getFirst().trainingOutput().length]; // difference between calculated preduction and given output in training data
            Double[] outputLayerDerivative = new Double[trainingDataRecordList.size()];
            double error = 0.0;

            //for loop after each training data record
            for (int trainingDataNumber = 0; trainingDataNumber < trainingDataRecordList.size(); trainingDataNumber++) {
                TrainingDataRecord dataRecord = trainingDataRecordList.get(trainingDataNumber);
                // checking fi data record has proper number of expected outputs
                if (dataRecord.trainingOutput().length != this.activationsMatrix.getLast().length) {
                    throw new WrongInputSizeException("Output size of given training data does not match size of neural network structure (output layer)");

                }
                // loop after each element in output layer
                // System.out.println("dataRecord.trainingOutput().length;:" + dataRecord.trainingOutput().length);
                for (int k = 0; k < dataRecord.trainingOutput().length; k++) {
                    calculatedPrediction[k] = this.step1FeedForward(dataRecord.trainingInput())[k];
                    difference[k] = calculatedPrediction[k] - dataRecord.trainingOutput()[k];
                    outputLayerDerivative[k] = 2 * difference[k];
                    error = error + Math.pow(difference[k],2);
                }

                //for loop after each layer (backwards, starting from output layer, ending at 1st hidden layer)
                for (int layer = this.activationsMatrix.size()-1; layer > 0; layer--) {
                    // System.out.println("layer:" + layer + " is output layer: " + (layer == (this.activationsMatrix.size()-1)));
                    // checking if actual layer is output layer
                    if (layer == (this.activationsMatrix.size()-1)) {
                        //for loop after each output element
                        for (int layerElement = 0; layerElement < this.activationsMatrix.getLast().length; layerElement++) {
                            // partial delta in output layer = output layer derivative * output activation function ( weighted sum)
                            partialDeltasList.get(layer)[layerElement] = outputLayerDerivative[layerElement] * this.runOutputActivationFunction(this.weightedSumsMatrix.get(layer)[layerElement],true);
                        }
                    } else {
                        //for loop after each output element
                        for (int layerElement = 0; layerElement < this.activationsMatrix.getLast().length; layerElement++) {
                            // partial delta in hidden layer = weights matrix (transposed) * partial deltas * hidden layer activation function ( weighted sum)


                            // slicing weights matrix (cutting of bias, column 0)
                            Double[][] slicedWeightsMatrix = NumPyLike.slice2DArray(this.weightsMatrix.get(layer+1), 0, this.weightsMatrix.get(layer+1).length-1, 1, this.weightsMatrix.get(layer+1)[layerElement].length - 1);
                            // transposing weights matrix and cutting of bias (index 0 in each layer)
                            Double[][] transposedAndSlicedWeightsMatrix = NumPyLike.transposeMatrix(slicedWeightsMatrix);

                            partialDeltasList.get(layer)[layerElement] = NumPyLike.matrixTimesVector(transposedAndSlicedWeightsMatrix,partialDeltasList.get(layer+1))[layerElement] * this.runHiddenActivationFunction(this.weightedSumsMatrix.get(layer)[layerElement],true);

                        }
                    }

                    Double[] doubles = this.activationsMatrix.get(layer - 1);
                    Double[] activationMatrixTransposedVector = new Double[this.activationsMatrix.get(layer-1).length];
                    for (int k = 0; k < this.activationsMatrix.get(layer-1).length; k++) {
                        activationMatrixTransposedVector[k] = this.activationsMatrix.get(layer-1)[k];
                        // System.out.println("activationMatrixTransposedVector[" + k + "]:" + activationMatrixTransposedVector[k]);
                    }
                    

                    // sum of weight deviations in each layer (sum in whole layer)

//                    for (int i = 0; i < deltaWlist.get(layer).length; i++) {
//                        for (int k = 0; k < deltaWlist.get(layer)[i].length; k++) {
//                            deltaWlist.get(layer)[i][k] = deltaWlist.get(layer)[i][k]*(-1)*this.eta;
//                            // System.out.println("deltaWlist.get(" + layer+ ")[" + i + "][" + k + "]" + deltaWlist.get(layer)[i][k]);
//                        }
//                    }
                    Double[][] deltaWToAdd = NumPyLike.vectorTimesTransposedVector(partialDeltasList.get(layer), activationMatrixTransposedVector);
                    for (int i = 0; i < deltaWToAdd.length; i++) {
                        for (int j = 0; j < deltaWToAdd[i].length; j++) {
                            deltaWToAdd[i][j] = deltaWToAdd[i][j]*(-1)*this.eta;
                        }
                    }
                    deltaWlist.set(layer,NumPyLike.add2Arrays(deltaWlist.get(layer),deltaWToAdd));




                }
            }

            //inculding deltaW into the weights
            for (int layer = 0; layer < deltaWlist.size(); layer++) {
                for (int layerElement = 0; layerElement < this.weightsMatrix.get(layer).length; layerElement++) {
                    for (int layerElement2 = 0; layerElement2 < this.weightsMatrix.get(layer)[layerElement].length; layerElement2++) {
                         Double adjustmentValue = deltaWlist.get(layer)[layerElement][layerElement2] / trainingDataRecordList.size();
                         // System.out.println("adjustmentValue:" + adjustmentValue);
                         this.weightsMatrix.get(layer)[layerElement][layerElement2] = this.weightsMatrix.get(layer)[layerElement][layerElement2] + adjustmentValue; // adjusting weight with delta W
                         deltaWlist.get(layer)[layerElement][layerElement2] = 0.0; //resetting delta W
                    }
                }
            }


            this.errorProgression.add(error); // adding current error to progression
            System.out.println("iteration:" + iteration + ", difference:" + Arrays.toString(difference) + ", error:" + error + ", caculatedPrediction:" + Arrays.toString(calculatedPrediction));
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
