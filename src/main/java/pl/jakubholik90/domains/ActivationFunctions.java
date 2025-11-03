package pl.jakubholik90.domains;

public abstract class ActivationFunctions {
    //set of activation functions for nodes

    // definition of Relu activation function
    public static double relu(double z, boolean calculateDerivative) {
        double returnValue = 0;
        if (calculateDerivative) {
            if (z >= 0) {
                returnValue = 1.0; //relu'(z) = 1 if z is positive
            } else {
                returnValue = 0.0; //relu'(z) = 0 if z is negative
            }
        } else {
            returnValue = Math.max(0.0,z);
            //relu(z) = z if z is positive
            //relu(z) = 0 if z is negative
        }
        return returnValue;
    }

    // definition of leaky Relu activation function (adapted 0 value)
    public static double leakyRelu(double z, boolean calculateDerivative) {
        double returnValue = 0;
        if (calculateDerivative) {
            if (z >= 0) {
                returnValue = 1.0; //relu'(z) = 1 if z is positive
            } else {
                returnValue = 0.1; //relu'(z) = 0.1 if z is negative
            }
        } else {
            if (z >= 0) {
                returnValue = z; //relu(z) = z if z is positive
            } else {
                returnValue = 0.1 * z; //relu(z) = 0.1*z if z is negative
            }
        }
        return returnValue;
    }

    // definition of sigmoid activation function
    public static double sigmoid(double z, boolean calculateDerivative) {
        double returnValue = 0;
        if (calculateDerivative) {
            returnValue = sigmoid(z,false) * (1 - sigmoid(z,false)); //derivative from sigmoid, recursive by one step
        } else {
            returnValue = 1 / (1 + Math.exp(-z));
        }
        return returnValue;
    }

    // definition of dummy activation function ( f(z) = z )
    public static double dummy(double z, boolean calculateDerivative) {
        double returnValue = 0;
        if (calculateDerivative) {
            returnValue = 1;
        } else {
            returnValue = z;
        }
        return returnValue;
    }

}
