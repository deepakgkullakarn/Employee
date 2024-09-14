package com.employee.exception;

public class EmployeeRelievedException extends RuntimeException{

    private String message;

    public EmployeeRelievedException() {}

    public EmployeeRelievedException(String msg) {
        super(msg);
        this.message = msg;
    }
}
