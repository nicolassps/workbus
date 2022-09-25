package br.com.workbus.exception;

public class PreStepFailedException extends RuntimeException{

    public PreStepFailedException(String message){
        super(message);
    }
}
