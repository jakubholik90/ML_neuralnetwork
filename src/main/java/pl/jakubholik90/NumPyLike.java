package pl.jakubholik90;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class NumPyLike {
    // set of methods analog to those from python numpy

    public static Double[] zeros(int size) {
        Double[] returnArray = new Double[size];
        for (int i = 0; i < size; i++) {
            returnArray[i] = 0.0;
        }
        return returnArray;
    }

    public static Double[][] zeros2D(int numberOfRows, int numberOfColumns) {
        Double[][] returnArray = new Double[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                returnArray[i][j] = 0.0;
            }
        }
        return returnArray;
    }

    public static Double[] ones(int size) {
        Double[] returnArray = new Double[size];
        for (int i = 0; i < size; i++) {
            returnArray[i] = 1.0;
        }
        return returnArray;
    }

    public static Double[][] randomMinus1to1(int numberOfRows, int numberOfColumns) {
        Double[][] returnArray = new Double[numberOfRows][numberOfColumns];
        Random random = new Random();
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                returnArray[i][j] = random.nextDouble(1) * 2 - 1;
            }
        }
        return returnArray;
    }

    public static Double[] matrixVectorMultiply(Double[][] inputMatrix, Double[] inputVector) {
        //checking if the input size is correct
        boolean checkSize = true;
        for (Double[] layer : inputMatrix) {
            if (layer.length != inputMatrix[0].length || inputVector.length != layer.length) {
                checkSize = false;
                break;
            }
        }

        if (!checkSize) {
            throw new WrongInputSizeException("inputVector size does not match inputMatrix size (num. of columns) of number of columnf within inputMatrix is not constant");
        }

        Double[] returnArray = new Double[inputMatrix.length];

        for (int i = 0; i < inputMatrix.length; i++) {
            double returnValue = 0;
            for (int j = 0; j < inputVector.length; j++) {
                returnValue = returnValue + inputVector[j] * inputMatrix[i][j];
                // System.out.println("step" + i + "/" + j + ": adding " + inputVector[j] + "*" + inputMatrix[i][j]);
            }
            returnArray[i] = returnValue;
        }
        return returnArray;
    }

    // skonczyc tu transponowanie macierzy
    public static Double[][] transpose(Double[][] inputMatrix) {
        Double[][] returnArray = new Double[inputMatrix[0].length][inputMatrix.length];
        for (int rows = 0; rows < inputMatrix.length; rows++) {
            for (int columns = 0; columns < inputMatrix[rows].length; columns++) {
                returnArray[columns][rows] = inputMatrix[rows][columns];
            }
        }
    return returnArray;
    }

}
