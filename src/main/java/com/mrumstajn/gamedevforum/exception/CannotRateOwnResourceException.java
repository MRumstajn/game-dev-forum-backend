package com.mrumstajn.gamedevforum.exception;

public class CannotRateOwnResourceException extends RuntimeException{

    public CannotRateOwnResourceException(String msg){
        super(msg);
    }
}
