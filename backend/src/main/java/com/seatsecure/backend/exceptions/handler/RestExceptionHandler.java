package com.seatsecure.backend.exceptions.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.seatsecure.backend.exceptions.EventCreationError;
import com.seatsecure.backend.exceptions.EventNotFoundException;
import com.seatsecure.backend.exceptions.UserNotFoundException;
import com.seatsecure.backend.exceptions.UsernameAlreadyExistsException;
import com.seatsecure.backend.exceptions.VenueCreationError;
import com.seatsecure.backend.exceptions.VenueNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, 
        HttpHeaders headers, 
        HttpStatusCode status, 
        WebRequest request) {
      List<String> errors = new ArrayList<String>();
      for (FieldError error : ex.getBindingResult().getFieldErrors()) {
          errors.add(error.getField() + ": " + error.getDefaultMessage());
      }
      for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
          errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
      }
      
      ApiError apiError = 
        new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
      return handleExceptionInternal(
        ex, apiError, headers, apiError.getStatus(), request);
    }

    // @ExceptionHandler({ ConstraintViolationException.class })
    // public ResponseEntity<Object> handleConstraintViolation(
    //     ConstraintViolationException ex,
    //     WebRequest request) {
    //   List<String> errors = new ArrayList<String>();
    //   for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
    //       errors.add(violation.getRootBeanClass().getName() + " " + 
    //         violation.getPropertyPath() + ": " + violation.getMessage());
    //   }

    //   ApiError apiError = 
    //     new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    //   return new ResponseEntity<Object>(
    //     apiError, new HttpHeaders(), apiError.getStatus());
    // }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(
      UsernameAlreadyExistsException exception
    ) {
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(exception.getMessage());
    }

    @ExceptionHandler(EventCreationError.class)
    public ResponseEntity<String> handleEventCreationError(
      EventCreationError exception
    ) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(exception.getMessage());
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<String> handleEventNotFoundException(
      EventNotFoundException exception
    ) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(
      UserNotFoundException exception
    ) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(exception.getMessage());
    }

    @ExceptionHandler(VenueCreationError.class)
    public ResponseEntity<String> handleVenueCreationError (
      VenueCreationError exception
    ) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(exception.getMessage());
    }

    @ExceptionHandler(VenueNotFoundException.class)
    public ResponseEntity<String> handleVenueNotFoundException(
      VenueNotFoundException exception
    ) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(exception.getMessage());
    }

}
