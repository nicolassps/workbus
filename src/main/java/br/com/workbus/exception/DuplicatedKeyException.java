package br.com.workbus.exception;

public class DuplicatedKeyException extends RuntimeException {
    public DuplicatedKeyException(String message){
        super(message);
    }
}
