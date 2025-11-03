package pl.jakubholik90.domains;

@FunctionalInterface
public interface ActivationFunctionInterface {
    double activationFunction(double z, boolean calculateDerivative);
}
