package org.example.bcm.common.exception;

public class TokenNotEnoughException extends RuntimeException{
    public TokenNotEnoughException(String message) {
        super(message);
    }
}
