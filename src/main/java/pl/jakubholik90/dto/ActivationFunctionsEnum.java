package pl.jakubholik90.dto;

import pl.jakubholik90.neuralnetwork.ActivationFunctionInterface;
import pl.jakubholik90.neuralnetwork.ActivationFunctions;

public enum ActivationFunctionsEnum {
    RELU("RELU", ActivationFunctions::relu),
    LEAKY_RELU("LEAKY_RELU", ActivationFunctions::leakyRelu),
    SIGMOID("SIGMOID", ActivationFunctions::sigmoid),
    DUMMY("DUMMY", ActivationFunctions::dummy);

    private final String name;
    public final ActivationFunctionInterface function;

    ActivationFunctionsEnum(String name, ActivationFunctionInterface function) {
        this.name = name;
        this.function = function;
    }
}
