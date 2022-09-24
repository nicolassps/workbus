package br.com.workbus.exception;

public class InvalidKeyException extends RuntimeException{
    private String message;

    public InvalidKeyException(String message){
        this.message = message;
    }
}
