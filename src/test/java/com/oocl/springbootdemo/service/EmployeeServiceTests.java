package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Employee;
import com.oocl.springbootdemo.exception.EmployeeNotAmongLegalAgeException;
import com.oocl.springbootdemo.exception.EmployeeNotFoundException;
import com.oocl.springbootdemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void should_return_employee_given_exist_employee_id() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Tom");
        employee.setAge(20);
        employee.setGender("Male");
        employee.setSalary(1000);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Employee foundEmployee = employeeService.getEmployeeById(1);
        assertEquals(employee, foundEmployee);
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    public void should_return_null_given_not_exist_employee_id() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Tom");
        employee.setAge(20);
        employee.setGender("Male");
        employee.setSalary(1000);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(999));
        assertEquals("Employee not found with id: 999", exception.getMessage());
        verify(employeeRepository, times(1)).findById(999);
    }
}
