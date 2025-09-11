package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee create(Employee employee);

    void clearAll();

    Employee findById(long id);

    List<Employee> findAll();

    Employee update(Employee employee);

    Employee delete(Employee employee);

    List<Employee> queryByGender(String gender);
}
