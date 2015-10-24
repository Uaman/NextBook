package com.nextbook.domain.exceptions;

/**
 * Created by oleh on 24.10.2015.
 */
public class EmailAlreadyExistsException extends Exception{

    public EmailAlreadyExistsException() { super(DEFAULT_MESSAGE); }

    public EmailAlreadyExistsException(String message) { super(message); }

    private static final String DEFAULT_MESSAGE = "This email already used";
}
