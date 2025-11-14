package pl.jakubholik90.neuralnetwork;

@FunctionalInterface
public interface ActivationFunctionInterface {
    double activationFunction(double z, boolean calculateDerivative);
}
