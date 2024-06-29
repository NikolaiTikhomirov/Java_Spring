package ru.gb.service;

public class ReaderAlreadyHasBookException extends RuntimeException{
    public ReaderAlreadyHasBookException(String message) {
        super(message);
    }
}
