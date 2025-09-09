package com.oocl.springbootdemo.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
        Optional<Employee> employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst();

        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<Employee>> queryEmployeeByGender(@RequestParam(required = false) String gender) {
        if(gender != null) {
            List<Employee> filteredEmployees = employees.stream()
                    .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                    .collect(Collectors.toList());

            if (filteredEmployees.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(filteredEmployees);
        }
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee employee, @PathVariable int id) {
        for (Employee findEmployee : employees) {
            if(findEmployee.getId() == id) {
                findEmployee.setName(employee.getName());
                findEmployee.setAge(employee.getAge());
                findEmployee.setGender(employee.getGender());
                findEmployee.setSalary(employee.getSalary());
                return ResponseEntity.ok(findEmployee);
            }
        }
        return ResponseEntity.notFound().build();
    }

    public void clearEmployees() {
        employees.clear();
    }
}
