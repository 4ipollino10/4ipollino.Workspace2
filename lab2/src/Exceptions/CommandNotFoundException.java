package Exceptions;

public class CommandNotFoundException extends Exception{
    public CommandNotFoundException(String errorText){
        super(errorText);
    }
}