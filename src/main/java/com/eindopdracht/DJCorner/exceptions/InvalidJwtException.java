package com.eindopdracht.DJCorner.exceptions;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException() {
        super();
    }

    public InvalidJwtException(String message) {
        super(message);
    }
}
