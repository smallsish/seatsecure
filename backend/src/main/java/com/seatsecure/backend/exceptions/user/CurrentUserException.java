package com.seatsecure.backend.exceptions.user;

public class CurrentUserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CurrentUserException() {
        super("The current user cannot be determined! Please try again later!");
    }
}
