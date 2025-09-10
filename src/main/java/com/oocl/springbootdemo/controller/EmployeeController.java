package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.Employee;
import com.oocl.springbootdemo.exception.EmployeeNotAmongLegalAgeException;
import com.oocl.springbootdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee createdEmployee = employeeService.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (EmployeeNotAmongLegalAgeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getEmployees(
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        Map<String, Object> employees = employeeService.getEmployees(gender, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee employee, @PathVariable int id) {
        Employee updatedEmployee = employeeService.updateEmployeeById(employee, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }
}
