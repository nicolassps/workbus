package br.com.workbus.exception;

public class DuplicatedKeyException extends RuntimeException {
    private String message;

    public DuplicatedKeyException(String message){
        this.message = message;
    }
}
