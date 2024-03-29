package com.zagvladimir.exception;

import com.zagvladimir.security.exception.TokenRefreshException;
import zagvladimir.utils.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {
  private static final String ERROR = "error";

  //TODO: add AccessDeniedException

  @ExceptionHandler({
    EmptyResultDataAccessException.class,
    NoSuchElementException.class,
    EntityNotFoundException.class
  })
  public ResponseEntity<Object> handlerEntityNotFoundException(Exception e) {
    ErrorContainer error =
        ErrorContainer.builder()
            .exceptionId(UUIDGenerator.generatedUI())
            .errorMessage(e.getMessage())
            .e(e.getClass().toString())
            .build();
    log.warn("error: {}, id: {}, error message {}",error.getErrorMessage(), error.getExceptionId(), error.getErrorMessage());

    return new ResponseEntity<>(Collections.singletonMap(ERROR, error), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({NumberFormatException.class})
  public ResponseEntity<Object> handlerNumberFormatException(Exception e) {

    ErrorContainer error =
        ErrorContainer.builder()
            .exceptionId(UUIDGenerator.generatedUI())
            .errorMessage(e.getMessage())
            .e(e.getClass().toString())
            .build();

    return new ResponseEntity<>(Collections.singletonMap(ERROR, error), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handlerException(Exception e) {

    ErrorContainer error =
        ErrorContainer.builder()
            .exceptionId(UUIDGenerator.generatedUI())
            .errorMessage(String.format("General error %s",e.getMessage()))
            .e(e.getClass().toString())
            .build();

    return new ResponseEntity<>(
        Collections.singletonMap(ERROR, error), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handlerDataIntegrityViolationException(Exception e) {

    ErrorContainer error =
        ErrorContainer.builder()
            .exceptionId(UUIDGenerator.generatedUI())
            .errorMessage("Data integrity violation")
            .e(e.getClass().toString())
            .build();

    return new ResponseEntity<>(Collections.singletonMap(ERROR, error), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handlerConstraintViolationException(
      ConstraintViolationException e) {

    Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
    Set<String> messages = new HashSet<>(constraintViolations.size());
    messages.addAll(
        constraintViolations.stream()
            .map(
                constraintViolation ->
                    String.format(
                        "%s value '%s' %s",
                        constraintViolation.getPropertyPath(),
                        constraintViolation.getInvalidValue(),
                        constraintViolation.getMessage()))
            .collect(Collectors.toList()));

    return new ResponseEntity<>(
        Collections.singletonMap(ERROR, messages), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handlerMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    Map<String, String> messages = new HashMap<>();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      messages.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return new ResponseEntity<>(
        Collections.singletonMap(ERROR, messages), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = TokenRefreshException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<Object> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
    ErrorContainer error =
            ErrorContainer.builder()
                    .exceptionId(UUIDGenerator.generatedUI())
                    .errorMessage(ex.getMessage())
                    .e(ex.getClass().toString())
                    .build();
    return new ResponseEntity<>(Collections.singletonMap(ERROR, error), HttpStatus.FORBIDDEN);
  }
}
