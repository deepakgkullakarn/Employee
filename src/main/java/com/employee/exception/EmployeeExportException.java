package com.employee.exception;

public class EmployeeExportException extends RuntimeException{

    private String message;

    public EmployeeExportException() {}

    public EmployeeExportException(String msg) {
        super(msg);
        this.message = msg;
    }
}