package ru.gulyaev.Exceptions;

public class MathException extends Exception{
    public MathException(String errorText){
        super(errorText);
    }
}