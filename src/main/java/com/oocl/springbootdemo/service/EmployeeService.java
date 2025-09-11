package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Employee;
import com.oocl.springbootdemo.exception.EmployeeNotFoundException;
import com.oocl.springbootdemo.exception.InvalidEmployeeAgeException;
import com.oocl.springbootdemo.exception.SalaryNotPatchEmployeeAgeException;
import com.oocl.springbootdemo.exception.UpdateLeftEmployeeException;
import com.oocl.springbootdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

   @Autowired
   private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        if(employee.getAge() < 18 || employee.getAge() > 65) {
            throw new InvalidEmployeeAgeException();
        }
        if(employee.getAge() > 30 && employee.getSalary() < 20000) {
            throw new SalaryNotPatchEmployeeAgeException();
        }
        return employeeRepository.create(employee);
    }

    public Employee getEmployeeById(int id) {
        Employee foundEmployee = employeeRepository.findById(id);
        if(foundEmployee == null) {
            throw new EmployeeNotFoundException();
        }
        return foundEmployee;
    }

    public Map<String, Object> getEmployees(String gender, int page, int size) {
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> filteredEmployees = employees;

        if (gender != null) {
            filteredEmployees = employeeRepository.queryByGender(gender);
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
        Employee foundEmployee = getEmployeeById(id);
        if (!foundEmployee.isStatus()) {
            throw new UpdateLeftEmployeeException();
        }
        return employeeRepository.update(employee);
    }

    public Employee deleteEmployeeById(int id) {
        Employee foundEmployee = getEmployeeById(id);
        return employeeRepository.delete(foundEmployee);
    }

    public void clearEmployees() {
        employeeRepository.clearAll();
    }
}
