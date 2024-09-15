package com.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(EmployeeRelievedException.class)
    public ResponseEntity<Object> handleEmplRelievedException(EmployeeRelievedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Employee has left the Organization");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchEmployeeExistsException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(NoSuchEmployeeExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
