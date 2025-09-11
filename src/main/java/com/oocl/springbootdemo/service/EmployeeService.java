package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.dto.EmployeeDto;
import com.oocl.springbootdemo.entity.Employee;
import com.oocl.springbootdemo.exception.EmployeeNotFoundException;
import com.oocl.springbootdemo.exception.InvalidEmployeeAgeException;
import com.oocl.springbootdemo.exception.SalaryNotPatchEmployeeAgeException;
import com.oocl.springbootdemo.exception.UpdateLeftEmployeeException;
import com.oocl.springbootdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getAge() < 18 || employeeDto.getAge() > 65) {
            throw new InvalidEmployeeAgeException();
        }
        if (employeeDto.getAge() >= 30 && employeeDto.getSalary() < 20000) {
            throw new SalaryNotPatchEmployeeAgeException();
        }
        Employee employee = employeeDtoMapping(employeeDto);
        return employeeRepository.create(employee);
    }

    public Employee getEmployeeById(long id) {
        Employee foundEmployee = employeeRepository.findById(id);
        if (foundEmployee == null) {
            throw new EmployeeNotFoundException();
        }
        return foundEmployee;
    }

    public Map<String, Object> getEmployees(String gender, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Employee> employees;
        if (gender != null) {
            employees = employeeRepository.findWithGenderAndPage(gender, pageable);
        } else {
            employees = employeeRepository.findWithPage(pageable);
        }

        int totalItems = (int) employees.getTotalElements();
        int totalPages = employees.getTotalPages();

        return getEmployeeResponse(employees.getContent(), page, size, totalPages, totalItems);
    }

    private Map<String, Object> getEmployeeResponse(List<Employee> pagedEmployeeDtos, int page, int size, int totalPages, int totalItems) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", pagedEmployeeDtos);
        response.put("totalPages", totalPages);
        response.put("totalItems", totalItems);
        response.put("currentPage", page);
        response.put("pageSize", size);
        return response;
    }

    public Employee updateEmployeeById(EmployeeDto employeeDto) {
        Employee foundEmployee = getEmployeeById(employeeDto.getId());
        if (!foundEmployee.getStatus()) {
            throw new UpdateLeftEmployeeException();
        }
        Employee employee = employeeDtoMapping(employeeDto);
        employee.setId(foundEmployee.getId());
        return employeeRepository.update(employee);
    }

    public Employee deleteEmployeeById(long id) {
        Employee foundEmployee = getEmployeeById(id);
        return employeeRepository.delete(foundEmployee);
    }

    public void clearEmployees() {
        employeeRepository.clearAll();
    }

    private Employee employeeDtoMapping(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setGender(employeeDto.getGender());
        employee.setAge(employeeDto.getAge());
        employee.setSalary(employeeDto.getSalary());
        employee.setCompanyId(employeeDto.getCompanyId());
        return employee;
    }
}
