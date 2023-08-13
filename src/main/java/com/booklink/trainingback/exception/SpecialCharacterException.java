package com.booklink.trainingback.exception;

public class SpecialCharacterException extends RuntimeException {
    public SpecialCharacterException() {
        super("Special characters not allowed here");
    }
}
