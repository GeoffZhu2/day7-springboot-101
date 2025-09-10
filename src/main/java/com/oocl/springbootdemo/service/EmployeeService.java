package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Employee;
import com.oocl.springbootdemo.exception.EmployeeCreateException;
import com.oocl.springbootdemo.exception.EmployeeNotFoundException;
import com.oocl.springbootdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        if(employee.getAge() < 18 || employee.getAge() > 65) {
            throw new EmployeeCreateException();
        }
        if(employee.getAge() > 30 && employee.getSalary() < 20000) {
            throw new EmployeeCreateException();
        }
        return employeeRepository.create(employee);
    }

    public Employee getEmployeeById(int id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    public Map<String, Object> getEmployees(String gender, int page, int size) {
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
            return getEmployeeResponse(employees, page, size, totalPages, totalItems);
        }
        int toIndex = Math.min(fromIndex + size, totalItems);

        List<Employee> pagedEmployees = filteredEmployees.subList(fromIndex, toIndex);

        return getEmployeeResponse(pagedEmployees, page, size, totalPages, totalItems);
    }

    private Map<String, Object> getEmployeeResponse(List<Employee> pagedEmployees, int page, int size, int totalPages, int totalItems) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", pagedEmployees);
        response.put("totalPages", totalPages);
        response.put("totalItems", totalItems);
        response.put("currentPage", page);
        response.put("pageSize", size);
        return response;
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
