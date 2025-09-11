package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.Employee;
import com.oocl.springbootdemo.repository.dao.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryInDBImpl implements EmployeeRepository {
    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Override
    public Employee create(Employee employee) {
        employee.setStatus(true);
        return employeeJpaRepository.save(employee);
    }

    @Override
    public void clearAll() {
        employeeJpaRepository.deleteAll();
    }

    @Override
    public Employee findById(long id) {
        return employeeJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findAll() {
        return employeeJpaRepository.findAll();
    }

    @Override
    public Employee update(Employee employee) {
        return employeeJpaRepository.save(employee);
    }

    @Override
    public Employee delete(Employee employee) {
        employee.setStatus(false);
        return employeeJpaRepository.save(employee);
    }

    @Override
    public List<Employee> queryByGender(String gender) {
        return employeeJpaRepository.findByGender(gender);
    }
}
