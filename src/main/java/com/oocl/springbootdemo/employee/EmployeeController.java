package com.oocl.springbootdemo.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    List<Employee> employees = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> queryEmployeeById(@PathVariable int id) {
        for (Employee employee : employees) {
            if(employee.getId() == id) {
                return ResponseEntity.ok(employee);
            }
        }
        return null;
    }
    @GetMapping("?gender=Male")
    public ResponseEntity<Employee> queryEmployeeByGender(@RequestParam String gender) {
        for (Employee employee : employees) {
            if(employee.getGender().equals(gender)) {
                return ResponseEntity.ok(employee);
            }
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee employee, @PathVariable int id) {
        for (Employee findEmployee : employees) {
            if(findEmployee.getId() == id) {
                findEmployee.setName(employee.getName());
                findEmployee.setAge(employee.getAge());
                findEmployee.setGender(employee.getGender());
                findEmployee.setSalary(employee.getSalary());
                return ResponseEntity.ok(employee);
            }
        }
        return null;
    }
}
