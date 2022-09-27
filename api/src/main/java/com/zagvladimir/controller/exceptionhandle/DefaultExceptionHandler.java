package com.zagvladimir.controller.exceptionhandle;




import com.zagvladimir.exception.NoSuchEntityException;
import com.zagvladimir.util.UUIDGenerator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({NoSuchEntityException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handlerEntityNotFoundException(Exception e) {

        ErrorContainer error =  ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generatedUI())
                .errorCode(2)
                .errorMessage(e.getMessage())
                .e(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error",error),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handlerNumberFormatException(Exception e) {

        ErrorContainer error =  ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generatedUI())
                .errorCode(3)
                .errorMessage(e.getMessage())
                .e(e.getClass().toString() + ": Incorrect ID entry")
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error",error),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception e) {

        ErrorContainer error=  ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generatedUI())
                .errorCode(1)
                .errorMessage("General error")
                .e(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error",error),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
