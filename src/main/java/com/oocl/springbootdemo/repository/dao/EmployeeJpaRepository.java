package com.oocl.springbootdemo.repository.dao;

import com.oocl.springbootdemo.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByGender(String gender, Pageable pageable);
}
