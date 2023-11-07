package com.seatsecure.backend.exceptions.not_found;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }

    public UserNotFoundException(String username) {
        super("Could not find user with username " + username);
    }
}
