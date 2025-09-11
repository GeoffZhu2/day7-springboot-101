package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository {
    Employee create(Employee employee);

    void clearAll();

    Employee findById(long id);

    Employee update(Employee employee);

    Employee delete(Employee employee);

    Page<Employee> findWithPage(Pageable pageable);

    Page<Employee> findWithGenderAndPage(String gender, Pageable pageable);
}
