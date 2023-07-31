package oisin.connery.exceptions;

public class ExpressionFormatException extends RuntimeException{
    public ExpressionFormatException(String errorMessage){
        this(errorMessage, null);
    }
    public ExpressionFormatException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }
}
