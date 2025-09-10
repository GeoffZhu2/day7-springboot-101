package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    List<Employee> employees = new ArrayList<>();
    static int employeeId = 0;

    public ResponseEntity<Employee> createEmployee(Employee employee) {
        employee.setId(++employeeId);
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    public ResponseEntity<Employee> getEmployeeById(int id) {
        Optional<Employee> employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Map<String, Object>> getEmployees(String gender, int page, int size) {
        List<Employee> filteredEmployees = employees;
        if (gender != null) {
            filteredEmployees = employees.stream()
                    .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                    .collect(Collectors.toList());
        }
        int totalItems = filteredEmployees.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int fromIndex = (page - 1) * size;
        if (fromIndex > totalItems) {
            return ResponseEntity.ok(Map.of(
                    "content", filteredEmployees,
                    "totalPages", totalPages,
                    "totalItems", totalItems,
                    "currentPage", page,
                    "pageSize", size
            ));
        }
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

    public ResponseEntity<Employee> updateEmployeeById(Employee employee, int id) {
        Optional<Employee> employeeOptional = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        if (employeeOptional.isPresent()) {
            Employee updatedEmployee = employeeOptional.get();
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setAge(employee.getAge());
            updatedEmployee.setGender(employee.getGender());
            updatedEmployee.setSalary(employee.getSalary());
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Employee> deleteEmployeeById(int id) {
        for (Employee findEmployee : employees) {
            if(findEmployee.getId() == id) {
                employees.remove(findEmployee);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.noContent().build();
    }

    public void clearEmployees() {
        employees.clear();
        employeeId = 0;
    }
}
