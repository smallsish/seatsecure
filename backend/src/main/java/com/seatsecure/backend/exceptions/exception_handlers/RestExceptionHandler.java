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

import com.seatsecure.backend.exceptions.BiddingNotCloseException;
import com.seatsecure.backend.exceptions.QueueNotFoundException;
import com.seatsecure.backend.exceptions.creation.QueueEntryCreationException;
import com.seatsecure.backend.exceptions.existence.SeatHasTicketException;
import com.seatsecure.backend.exceptions.existence.TicketHasOwnerException;
import com.seatsecure.backend.exceptions.existence.TicketHasRunException;
import com.seatsecure.backend.exceptions.not_found.CatNotFoundException;
import com.seatsecure.backend.exceptions.not_found.EventNotFoundException;
import com.seatsecure.backend.exceptions.not_found.QueueEntryNotFoundException;
import com.seatsecure.backend.exceptions.not_found.RunNotFoundException;
import com.seatsecure.backend.exceptions.not_found.SeatNotFoundException;
import com.seatsecure.backend.exceptions.not_found.TicketNotFoundException;
import com.seatsecure.backend.exceptions.not_found.UserNotFoundException;
import com.seatsecure.backend.exceptions.not_found.VenueNotFoundException;
import com.seatsecure.backend.exceptions.null_property.NullCatException;
import com.seatsecure.backend.exceptions.null_property.NullEventException;
import com.seatsecure.backend.exceptions.null_property.NullRunException;
import com.seatsecure.backend.exceptions.null_property.NullSeatException;
import com.seatsecure.backend.exceptions.null_property.NullUserException;
import com.seatsecure.backend.exceptions.null_property.NullVenueException;
import com.seatsecure.backend.exceptions.others.CurrentUserException;
import com.seatsecure.backend.exceptions.others.RegistrationValidationException;
import com.seatsecure.backend.exceptions.others.UnauthorizedUserException;
import com.seatsecure.backend.exceptions.others.UsernameAlreadyExistsException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  // This exception occurs when during the registration process of a new user,
  // some fields fail to meet the validation requirements
  @ExceptionHandler(RegistrationValidationException.class)
  public ResponseEntity<Object> handleRegistrationValidationException(RegistrationValidationException ex) {
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

  // These exceptions are usually raised when the "getSomethingById" methods
  // return null.
  // They imply that the requested object does not exist in the database, hence
  // causing the request
  // to fail gracefully.
  @ExceptionHandler({ CatNotFoundException.class, EventNotFoundException.class, QueueEntryNotFoundException.class,
      RunNotFoundException.class,
      SeatNotFoundException.class, TicketNotFoundException.class, UserNotFoundException.class,
      VenueNotFoundException.class })
  public ResponseEntity<Object> handleNotFoundExceptions(RuntimeException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  }

  // These exceptions occur as a result of properties of entities being null, even
  // though they should not.
  // These exceptions usually imply an error with internal implementation and can
  // be deterimental.
  @ExceptionHandler({ NullCatException.class, NullEventException.class, NullRunException.class, NullSeatException.class,
      NullUserException.class, NullVenueException.class })
  public ResponseEntity<Object> handleNullPropertyExceptions(RuntimeException exception) {
    String message = "An error has occurred. Please try again later.";
    return ResponseHandler.generateResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
  }

  // These exceptions state that a certain property of an entity is already set.
  // and must be unset, otherwise they will be overwritten.
  @ExceptionHandler({ SeatHasTicketException.class, TicketHasOwnerException.class, TicketHasRunException.class })
  public ResponseEntity<Object> handleExistenceExceptions(RuntimeException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.CONFLICT, null);
  }

  // This exception indicates that a user is not authorized to perform
  // a particular action
  @ExceptionHandler(UnauthorizedUserException.class)
  public ResponseEntity<Object> handleUnauthorizedUserException(
      UnauthorizedUserException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED, null);
  }

  // This exception indicates that there is an error with getting the user in the
  // current security context
  @ExceptionHandler(CurrentUserException.class)
  public ResponseEntity<Object> handleCurrentUserException(CurrentUserException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(),
        HttpStatus.NOT_FOUND, null);
  }

  // This exception indicates that during the registration process of a new user,
  // the user has picked a username that already exists
  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception) {
    return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.CONFLICT, null);
  }

  // BIDDING EXCEPTIONS //
  // @ExceptionHandler(QueueNotFoundException.class)
  // public ResponseEntity<Object> handleQueueNotFoundException(QueueNotFoundException exception) {
  //   return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  // }

  // @ExceptionHandler(QueueEntryNotFoundException.class)
  // public ResponseEntity<Object> handleQueueEntryNotFoundException(QueueEntryNotFoundException exception) {
  //   return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  // }

  // @ExceptionHandler(QueueEntryCreationException.class)
  // public ResponseEntity<Object> handleQueueEntryCreationException(QueueEntryCreationException exception) {
  //   return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  // }

  // @ExceptionHandler(BiddingNotCloseException.class)
  // public ResponseEntity<Object> handleBiddingNotCloseException(BiddingNotCloseException exception) {
  //   return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null);
  // }
}
