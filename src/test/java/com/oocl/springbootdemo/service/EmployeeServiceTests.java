package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Employee;
import com.oocl.springbootdemo.exception.EmployeeCreateException;
import com.oocl.springbootdemo.exception.EmployeeNotFoundException;
import com.oocl.springbootdemo.exception.SalaryNotPatchEmployeeAgeException;
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
        employee1.setSalary(25000);
        Employee employee2 = new Employee();
        employee1.setId(2);
        employee1.setAge(66);
        employee1.setSalary(25000);
        assertThrows(EmployeeCreateException.class, () -> employeeService.createEmployee(employee1));
        assertThrows(EmployeeCreateException.class, () -> employeeService.createEmployee(employee2));
    }

    @Test
    public void should_not_create_employee_given_age_over_30_and_salary_below_20000() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Tom");
        employee.setAge(31);
        employee.setGender("Male");
        employee.setSalary(2222);

        assertThrows(SalaryNotPatchEmployeeAgeException.class, () -> employeeService.createEmployee(employee));
    }

    @Test
    public void should_create_an_active_status_employee_given_false_status_employee() {
        Employee expectEmployee = new Employee();
        expectEmployee.setId(1);
        expectEmployee.setName("Tom");
        expectEmployee.setAge(31);
        expectEmployee.setGender("Male");
        expectEmployee.setSalary(35000);
        expectEmployee.setStatus(true);

        Employee employee = new Employee();
        employee.setName("Tom");
        employee.setAge(31);
        employee.setGender("Male");
        employee.setSalary(35000);
        when(employeeRepository.create(employee)).thenReturn(expectEmployee);
        Employee foundEmployee = employeeService.createEmployee(employee);
        assertTrue(foundEmployee.isStatus());
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
