package com.zagvladimir.exception;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorContainer {

    private String exceptionId;

    private String errorMessage;

    private int errorCode;

    private String e;

}
