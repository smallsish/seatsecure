package com.seatsecure.backend.exceptions.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.seatsecure.backend.exceptions.CurrentUserException;
import com.seatsecure.backend.exceptions.EventCreationError;
import com.seatsecure.backend.exceptions.EventNotFoundException;
import com.seatsecure.backend.exceptions.RegistrationValidationError;
import com.seatsecure.backend.exceptions.RunNotFoundException;
import com.seatsecure.backend.exceptions.UnauthorizedUserException;
import com.seatsecure.backend.exceptions.UserNotFoundException;
import com.seatsecure.backend.exceptions.UsernameAlreadyExistsException;
import com.seatsecure.backend.exceptions.VenueCreationError;
import com.seatsecure.backend.exceptions.VenueNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  //@Override

  // protected ResponseEntity<Object> handleMethodArgumentNotValid(
  //     MethodArgumentNotValidException ex,
  //     HttpHeaders headers,
  //     HttpStatusCode status,
  //     WebRequest request) {
  //   List<String> errors = new ArrayList<String>();
  //   for (FieldError error : ex.getBindingResult().getFieldErrors()) {
  //     errors.add(error.getField() + ": " + error.getDefaultMessage());
  //   }
  //   for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
  //     errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
  //   }

  //   ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
  //   return handleExceptionInternal(
  //       ex, apiError, headers, apiError.getStatus(), request);
  // }

  @ExceptionHandler(RegistrationValidationError.class)
  public ResponseEntity<Object> handleRegistrationValidationError(RegistrationValidationError ex) {
    String errorMsg = ex.getMessage();
    BindingResult bindingResult = ex.getBindingResult();
    List<String> fieldErrors = new ArrayList<>();

    errorMsg += "\nErrors detected with the following fields:\n";
    for (FieldError err : bindingResult.getFieldErrors()) {
      String fieldName = err.getField();
      errorMsg += "\n- " + fieldName + "\n";
      fieldErrors.add(fieldName);
    }

    return ResponseHandler.generateResponse(errorMsg, HttpStatus.CONFLICT, fieldErrors);
  }


  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<Object> handleNoSuchElementFoundException(
      UsernameAlreadyExistsException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.CONFLICT, null);
  }

  @ExceptionHandler(EventCreationError.class)
  public ResponseEntity<Object> handleEventCreationError(
      EventCreationError exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  }

  @ExceptionHandler(EventNotFoundException.class)
  public ResponseEntity<Object> handleEventNotFoundException(
      EventNotFoundException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> handleUserNotFoundException(
      UserNotFoundException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  }

  @ExceptionHandler(VenueCreationError.class)
  public ResponseEntity<Object> handleVenueCreationError(
      VenueCreationError exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  }

  @ExceptionHandler(VenueNotFoundException.class)
  public ResponseEntity<Object> handleVenueNotFoundException(
      VenueNotFoundException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  }

  @ExceptionHandler(UnauthorizedUserException.class)
  public ResponseEntity<Object> handleUnauthorizedUserException(
    UnauthorizedUserException exception) {
      return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED, null);
    }

  @ExceptionHandler(CurrentUserException.class)
  public ResponseEntity<Object> handleCurrentUserException(CurrentUserException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  }

  @ExceptionHandler(RunNotFoundException.class)
  public ResponseEntity<Object> handleRunNotFoundException(RunNotFoundException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  }

}