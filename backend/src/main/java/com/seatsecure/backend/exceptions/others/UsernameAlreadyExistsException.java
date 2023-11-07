package com.seatsecure.backend.exceptions.others;

public class UsernameAlreadyExistsException extends RuntimeException {
    
    public UsernameAlreadyExistsException(String username) {
        super(String.format("The user %s already exists!", username));
    }

}
