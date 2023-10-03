package com.seatsecure.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyExistsException extends RuntimeException {
    
    public UsernameAlreadyExistsException(final String msg) {
        super(msg);
    }

}
