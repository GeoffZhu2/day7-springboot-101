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
    public ResponseEntity<Map<String, Object>> getEmployees(
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<Employee> filteredEmployees = employees;
        if (gender != null) {
            filteredEmployees = employees.stream()
                    .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                    .collect(Collectors.toList());
        }
        int totalItems = filteredEmployees.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, totalItems);

        List<Employee> pagedEmployees = filteredEmployees.subList(fromIndex, toIndex);

        return ResponseEntity.ok(Map.of(
                "content", pagedEmployees,
                "totalPages", totalPages,
                "totalItems", totalItems,
                "currentPage", page,
                "pageSize", size
        ));
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable int id) {
        for (Employee findEmployee : employees) {
            if(findEmployee.getId() == id) {
                employees.remove(findEmployee);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.noContent().build();
    }

    public void clearEmployees() {
        employees.clear();
    }
}
