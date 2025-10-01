package pl.jakubholik90;

import java.util.Random;

public abstract class NumPyLike {
    // set of methods analog to those from python numpy and other mathematic covensions

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

    public static Double[] matrixTimesVector (Double[][] inputMatrix, Double[] inputVector) {
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

    public static Double[][] vectorTimesTransposedVector (Double[] inputVector, Double[][] inputTransposedVector) {
        //checking if the input size is correct
        if (inputTransposedVector.length != 1) {
            throw new WrongInputSizeException("inputTransposedVector have size > 1 (size:" + inputTransposedVector.length+ ")");
        }

        Double[][] returnArray = new Double[inputVector.length][inputTransposedVector.length];

        for (int rows = 0; rows < inputVector.length; rows++) {
            double returnValue = 0;
            for (int columns = 0; columns < inputTransposedVector.length; columns++) {
                returnArray[rows][columns] = inputVector[rows]*inputTransposedVector[columns][0];
            }
        }
        return returnArray;
    }

    //transposing of matrix
    public static Double[][] transposeMatrix (Double[][] inputMatrix) {
        Double[][] returnArray = new Double[inputMatrix[0].length][inputMatrix.length];
        for (int rows = 0; rows < inputMatrix.length; rows++) {
            for (int columns = 0; columns < inputMatrix[rows].length; columns++) {
                returnArray[columns][rows] = inputMatrix[rows][columns];
            }
        }
    return returnArray;
    }




    // slicing of 2d Array
    public static Double[][] slice2DArray(Double[][] inputArray, int rowStart, int rowEnd, int colStart, int colEnd) {
        boolean rowStartPos = (rowStart>=0);
        boolean colStartPos = (colStart>=0);
        boolean rowEndOK = (rowEnd>=rowStart);
        boolean colEndOK = (colEnd>=colStart);

        if (!rowStartPos) {
            throw new WrongInputSizeException("Incorrect input, slice2DArray cannot be performed. rowStart is negative");
        }
        if (!colStartPos) {
            throw new WrongInputSizeException("Incorrect input, slice2DArray cannot be performed. colStart is negative");
        }
        if (!rowEndOK) {
            throw new WrongInputSizeException("Incorrect input, slice2DArray cannot be performed. rowEnd<rowStart");
        }
        if (!colEndOK) {
            throw new WrongInputSizeException("Incorrect input, slice2DArray cannot be performed. colEnd<colStart");
        }
        int rowSize = rowEnd-rowStart+1;
        int colSize = colEnd-colStart+1;
        Double[][] returnArray = new Double[rowSize][colSize];

        int currentOldRow = rowStart;
        for (int newRow = 0; newRow < rowSize; newRow++) {
            int currentOldCol = colStart;
            for (int newCol = 0; newCol < colSize; newCol++) {
                returnArray[newRow][newCol] = inputArray[currentOldRow][currentOldCol];
                currentOldCol++;
            }
            currentOldRow++;
        }
        return returnArray;
    }

    // adding of two 2d Arrays
    public static Double[][] add2Arrays(Double[][] input1, Double[][] input2) {
        boolean rowsOK = (input1.length == input2.length);
        if (!rowsOK) {
            throw new WrongInputSizeException("Number of rows incorrect. Input 1 does not match Input 2");
        }
        boolean columnsOK = true;
        for (int i = 0; i < input1.length; i++) {
            columnsOK = (input1[i].length == input2[i].length);
            if (!columnsOK) {
                throw new WrongInputSizeException("Number of columns incorrect. Input 1 does not match Input 2");
            }
        }
        Double[][] returnArray = new Double[input1.length][input1[0].length];
        for (int i = 0; i < input1.length; i++) {
            for (int j = 0; j < input1[i].length; j++) {
                returnArray[i][j] = input1[i][j] + input2[i][j];
            }
        }

        return returnArray;
    }

}
