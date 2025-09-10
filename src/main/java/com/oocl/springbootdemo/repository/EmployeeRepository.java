package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.Employee;
import com.oocl.springbootdemo.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();
    private int employeeId = 0;

    public Employee create(Employee employee) {
        employee.setId(++employeeId);
        employee.setStatus(true);
        employees.add(employee);
        return employee;
    }

    public void clearAll() {
        employees.clear();
        employeeId = 0;
    }

    public Employee findById(int id) {
        return employees.stream().filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }


    public List<Employee> findAll() {
        return employees;
    }

    public Employee update(Employee employee) {
        employee.setName(employee.getName());
        employee.setAge(employee.getAge());
        employee.setGender(employee.getGender());
        employee.setSalary(employee.getSalary());
        employee.setStatus(employee.isStatus());
        return employee;
    }

    public Employee delete(int id) {
        Employee foundEmployee = findById(id);
        if(foundEmployee == null) {
            throw new EmployeeNotFoundException();
        }
        foundEmployee.setStatus(false);
        return foundEmployee;
    }
}
