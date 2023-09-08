package com.samuli.javafullstackbackend.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(){
        super("The provided email address is already registered.");
    }
}
