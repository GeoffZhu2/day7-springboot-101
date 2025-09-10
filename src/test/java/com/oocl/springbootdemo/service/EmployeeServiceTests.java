package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Employee;
import com.oocl.springbootdemo.exception.EmployeeNotAmongLegalAgeException;
import com.oocl.springbootdemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void should_not_create_employee_employee_given_invalid_age() {
        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setAge(17);
        Employee employee2 = new Employee();
        employee1.setId(2);
        employee1.setAge(66);
        assertThrows(EmployeeNotAmongLegalAgeException.class, () -> employeeService.createEmployee(employee1));
        assertThrows(EmployeeNotAmongLegalAgeException.class, () -> employeeService.createEmployee(employee2));
    }
}
