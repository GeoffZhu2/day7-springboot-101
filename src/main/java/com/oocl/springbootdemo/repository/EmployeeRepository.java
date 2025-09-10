package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();
    private static int employeeId = 0;

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

    public Optional<Employee> findById(int id) {
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee update(Employee employee, int id) {
        Optional<Employee> employeeOptional = findById(id);
        if(employeeOptional.isPresent()) {
            Employee updatedEmployee = employeeOptional.get();
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setAge(employee.getAge());
            updatedEmployee.setGender(employee.getGender());
            updatedEmployee.setSalary(employee.getSalary());
            return updatedEmployee;
        }
        return null;
    }

    public void delete(int id) {
        employees.removeIf(findEmployee -> findEmployee.getId() == id);
    }
}
