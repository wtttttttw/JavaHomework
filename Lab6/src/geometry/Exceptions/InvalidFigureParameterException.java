package geometry.Exceptions;

public class InvalidFigureParameterException extends Exception{
    public InvalidFigureParameterException(String message) {
        super(message);
    }
    
    public InvalidFigureParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
