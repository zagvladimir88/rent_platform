package com.zagvladimir.exception;

import com.zagvladimir.util.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

  @ExceptionHandler({
    NoSuchEntityException.class,
    EmptyResultDataAccessException.class,
    NoSuchElementException.class,
    EntityNotFoundException.class
  })
  public ResponseEntity<Object> handlerEntityNotFoundException(Exception e) {

    ErrorContainer error =
        ErrorContainer.builder()
            .exceptionId(UUIDGenerator.generatedUI())
            .errorCode(2)
            .errorMessage(e.getMessage())
            .e(e.getClass().toString())
            .build();
    log.warn("error: {}, id: {}, error code: {}",error.getErrorMessage(), error.getExceptionId(),error.getErrorCode());
    return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({NumberFormatException.class})
  public ResponseEntity<Object> handlerNumberFormatException(Exception e) {

    ErrorContainer error =
        ErrorContainer.builder()
            .exceptionId(UUIDGenerator.generatedUI())
            .errorCode(3)
            .errorMessage(e.getMessage())
            .e(e.getClass().toString())
            .build();

    return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handlerException(Exception e) {

    ErrorContainer error =
        ErrorContainer.builder()
            .exceptionId(UUIDGenerator.generatedUI())
            .errorCode(1)
            .errorMessage("General error")
            .e(e.getClass().toString())
            .build();

    return new ResponseEntity<>(
        Collections.singletonMap("error", error), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handlerDataIntegrityViolationException(Exception e) {

    ErrorContainer error =
        ErrorContainer.builder()
            .exceptionId(UUIDGenerator.generatedUI())
            .errorCode(1)
            .errorMessage("Data integrity violation")
            .e(e.getClass().toString())
            .build();

    return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.CONFLICT);
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
        Collections.singletonMap("error", messages), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handlerMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    Map<String, String> messages = new HashMap<>();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      messages.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return new ResponseEntity<>(
        Collections.singletonMap("error", messages), HttpStatus.BAD_REQUEST);
  }
}
