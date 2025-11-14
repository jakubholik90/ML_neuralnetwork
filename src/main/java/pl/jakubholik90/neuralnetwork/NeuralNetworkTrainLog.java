package pl.jakubholik90.neuralnetwork;

public class NeuralNetworkTrainLog {
    private final int epochs;
    private Double[] errorPerEpoch;

    public NeuralNetworkTrainLog(int epochs) {
        this.epochs = epochs;
        this.errorPerEpoch = new Double[epochs];
    }

    public void setErrorForEpoch(int epoch, Double error) {
        if(epoch < 0 || epoch >= epochs) {
            throw new IllegalArgumentException("Epoch index out of bounds");
        }

        this.errorPerEpoch[epoch] = error;
    }

    public int getEpochs() {
        return epochs;
    }

    public Double[] getErrorPerEpoch() {
        return errorPerEpoch;
    }
}
