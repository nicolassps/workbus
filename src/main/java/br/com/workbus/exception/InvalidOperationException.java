package br.com.workbus.exception;

public class InvalidOperationException extends RuntimeException{
    private String message;

    public InvalidOperationException(String message){
        this.message = message;
    }
}
