package pl.jakubholik90;

public abstract class NumPyLike {
    // set of methods analog to those from python numpy

    public static Double[][] zeros(int size) {
        Double[][] returnArray = new Double[size][1];
        for (int i = 0; i < size; i++) {
            returnArray[i][0] = 0.0;
        }
        return returnArray;
    }

    public static Double[][] ones(int size) {
        Double[][] returnArray = new Double[size][1];
        for (int i = 0; i < size; i++) {
            returnArray[i][0] = 1.0;
        }
        return returnArray;
    }
}
