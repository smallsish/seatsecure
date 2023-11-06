package com.seatsecure.backend.exceptions.user;

public class UsernameAlreadyExistsException extends RuntimeException {
    
    public UsernameAlreadyExistsException(final String msg) {
        super(msg);
    }

}
