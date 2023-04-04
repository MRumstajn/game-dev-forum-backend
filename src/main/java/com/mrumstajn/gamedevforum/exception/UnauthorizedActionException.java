package com.mrumstajn.gamedevforum.exception;

public class UnauthorizedActionException extends RuntimeException {

    public UnauthorizedActionException(String msg) {
        super(msg);
    }
}
