package geometry.Exceptions;

public class NegativeValueException extends InvalidFigureParameterException {
    public NegativeValueException(String parameterName, double value) {
        super("Parameter '" + parameterName + "' cannot be negative. Received: " + value);
    }
    
    public NegativeValueException(String parameterName, double value, Throwable cause) {
        super("Parameter '" + parameterName + "' cannot be negative. Received: " + value, cause);
    }
}
