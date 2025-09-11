package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Employee delete(Employee employee) {
        employee.setStatus(false);
        return employee;
    }

    public List<Employee> queryByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }
}
