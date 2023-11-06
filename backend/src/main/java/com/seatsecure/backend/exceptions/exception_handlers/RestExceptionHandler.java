package com.seatsecure.backend.exceptions.exception_handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.seatsecure.backend.exceptions.category.CatNotFoundException;
import com.seatsecure.backend.exceptions.event.EventCreationException;
import com.seatsecure.backend.exceptions.event.EventNotFoundException;
import com.seatsecure.backend.exceptions.event.NullEventException;
import com.seatsecure.backend.exceptions.run.RunNotFoundException;
import com.seatsecure.backend.exceptions.user.CurrentUserException;
import com.seatsecure.backend.exceptions.user.RegistrationValidationException;
import com.seatsecure.backend.exceptions.user.UnauthorizedUserException;
import com.seatsecure.backend.exceptions.user.UserNotFoundException;
import com.seatsecure.backend.exceptions.user.UsernameAlreadyExistsException;
import com.seatsecure.backend.exceptions.venue.VenueCreationException;
import com.seatsecure.backend.exceptions.venue.VenueNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  // @Override

  // protected ResponseEntity<Object> handleMethodArgumentNotValid(
  // MethodArgumentNotValidException ex,
  // HttpHeaders headers,
  // HttpStatusCode status,
  // WebRequest request) {
  // List<String> errors = new ArrayList<String>();
  // for (FieldError error : ex.getBindingResult().getFieldErrors()) {
  // errors.add(error.getField() + ": " + error.getDefaultMessage());
  // }
  // for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
  // errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
  // }

  // ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
  // ex.getLocalizedMessage(), errors);
  // return handleExceptionInternal(
  // ex, apiError, headers, apiError.getStatus(), request);
  // }


  @ExceptionHandler(RegistrationValidationException.class)
  public ResponseEntity<Object> handleRegistrationValidationError(RegistrationValidationException ex) {
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

  @ExceptionHandler({EventCreationException.class, EventNotFoundException.class, UserNotFoundException.class,
      VenueCreationException.class, VenueNotFoundException.class, CurrentUserException.class,
      RunNotFoundException.class, CatNotFoundException.class})
  public ResponseEntity<Object> handleNotFoundExceptions(RuntimeException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  }

  @ExceptionHandler({NullEventException.class})
  public ResponseEntity<Object> handleInternalExceptions(RuntimeException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
  }







  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<Object> handleNoSuchElementFoundException(
      UsernameAlreadyExistsException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.CONFLICT, null);
  }
  @ExceptionHandler(UnauthorizedUserException.class)
  public ResponseEntity<Object> handleUnauthorizedUserException(
      UnauthorizedUserException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED, null);
  }

  // @ExceptionHandler(EventCreationException.class)
  // public ResponseEntity<Object> handleEventCreationError(
  // EventCreationException exception) {
  // return ResponseHandler.generateResponse(exception.getMessage(),
  // HttpStatus.NOT_FOUND, null);
  // }

  // @ExceptionHandler(EventNotFoundException.class)
  // public ResponseEntity<Object> handleEventNotFoundException(
  // EventNotFoundException exception) {
  // return ResponseHandler.generateResponse(exception.getMessage(),
  // HttpStatus.NOT_FOUND, null);
  // }

  // @ExceptionHandler(UserNotFoundException.class)
  // public ResponseEntity<Object> handleUserNotFoundException(
  // UserNotFoundException exception) {
  // return ResponseHandler.generateResponse(exception.getMessage(),
  // HttpStatus.NOT_FOUND, null);
  // }

  // @ExceptionHandler(VenueCreationException.class)
  // public ResponseEntity<Object> handleVenueCreationError(
  // VenueCreationException exception) {
  // return ResponseHandler.generateResponse(exception.getMessage(),
  // HttpStatus.NOT_FOUND, null);
  // }

  // @ExceptionHandler(VenueNotFoundException.class)
  // public ResponseEntity<Object> handleVenueNotFoundException(
  // VenueNotFoundException exception) {
  // return ResponseHandler.generateResponse(exception.getMessage(),
  // HttpStatus.NOT_FOUND, null);
  // }

  // @ExceptionHandler(CurrentUserException.class)
  // public ResponseEntity<Object> handleCurrentUserException(CurrentUserException
  // exception) {
  // return ResponseHandler.generateResponse(exception.getMessage(),
  // HttpStatus.NOT_FOUND, null);
  // }

  // @ExceptionHandler(RunNotFoundException.class)
  // public ResponseEntity<Object> handleRunNotFoundException(RunNotFoundException
  // exception) {
  // return ResponseHandler.generateResponse(exception.getMessage(),
  // HttpStatus.NOT_FOUND, null);
  // }

}
