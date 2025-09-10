package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Employee;
import com.oocl.springbootdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService { 
    
   @Autowired
   private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.create(employee);
    }

    public Employee getEmployeeById(int id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.orElse(null);
    }

    public ResponseEntity<Map<String, Object>> getEmployees(String gender, int page, int size) {
        List<Employee> employees = employeeRepository.findAll();
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
            Map<String, Object> response = new HashMap<>();
            response.put("content", List.of());
            response.put("totalPages", totalPages);
            response.put("totalItems", totalItems);
            response.put("currentPage", page);
            response.put("pageSize", size);
            return ResponseEntity.ok(response);
        }
        int toIndex = Math.min(fromIndex + size, totalItems);

        List<Employee> pagedEmployees = filteredEmployees.subList(fromIndex, toIndex);

        Map<String, Object> response = new HashMap<>();
        response.put("content", pagedEmployees);
        response.put("totalPages", totalPages);
        response.put("totalItems", totalItems);
        response.put("currentPage", page);
        response.put("pageSize", size);
        return ResponseEntity.ok(response);
    }

    public Employee updateEmployeeById(Employee employee, int id) {
        return employeeRepository.update(employee, id);
    }

    public void deleteEmployeeById(int id) {
        employeeRepository.delete(id);
    }

    public void clearEmployees() {
        employeeRepository.clearAll();
    }
}
