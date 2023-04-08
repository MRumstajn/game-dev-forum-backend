package com.mrumstajn.gamedevforum.exception;

public class CannotFollowSelfException extends RuntimeException{

    public CannotFollowSelfException(String msg){
        super(msg);
    }
}
