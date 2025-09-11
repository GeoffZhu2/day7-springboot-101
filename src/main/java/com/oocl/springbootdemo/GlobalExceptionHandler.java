package com.oocl.springbootdemo;

import com.oocl.springbootdemo.exception.*;
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

    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCompanyNotFoundException(Exception e) {
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
