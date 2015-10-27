package com.nextbook.domain.exceptions;

/**
 * Created by oleh on 24.10.2015.
 */
public class IsbnAlreadyExistsException extends Exception{

    public IsbnAlreadyExistsException() { super(DEFAULT_MESSAGE); }

    public IsbnAlreadyExistsException(String message) { super(message); }

    private static final String DEFAULT_MESSAGE = "This isbn already used";
}