package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.Employee;
import com.oocl.springbootdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
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

    @PutMapping
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployeeById(employee);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }
}
