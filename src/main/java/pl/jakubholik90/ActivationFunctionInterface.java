package pl.jakubholik90;

@FunctionalInterface
public interface ActivationFunctionInterface {
    double activationFunction(double z, boolean calculateDerivative);
}
