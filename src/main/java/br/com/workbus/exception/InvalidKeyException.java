package br.com.workbus.exception;

public class InvalidKeyException extends RuntimeException{
    public InvalidKeyException(String message){
        super(message);
    }
}
