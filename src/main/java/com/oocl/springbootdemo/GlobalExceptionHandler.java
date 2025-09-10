package com.oocl.springbootdemo;

import com.oocl.springbootdemo.exception.EmployeeNotFoundException;
import com.oocl.springbootdemo.exception.InvalidEmployeeAgeException;
import com.oocl.springbootdemo.exception.SalaryNotPatchEmployeeAgeException;
import com.oocl.springbootdemo.exception.UpdateLeftEmployeeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidEmployeeAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidEmployeeAgeException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFoundException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(SalaryNotPatchEmployeeAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSalaryNotPatchEmployeeAgeException(Exception e) {
        return e.getMessage();
    }
    @ExceptionHandler(UpdateLeftEmployeeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUpdateHasLeftEmployeeException(Exception e) {
        return e.getMessage();
    }


}
