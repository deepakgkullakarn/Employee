package com.employee.exception;

import org.springframework.http.HttpStatus;

public class NoSuchEmployeeExistsException extends RuntimeException{

    private String message;
    private HttpStatus statuscode;


    public NoSuchEmployeeExistsException(HttpStatus status,String msg) {
        super(msg);
        this.message = msg;
        this.statuscode=status;
    }
}
