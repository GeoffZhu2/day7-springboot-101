package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.dto.EmployeeDto;
import com.oocl.springbootdemo.entity.Employee;
import com.oocl.springbootdemo.exception.EmployeeNotFoundException;
import com.oocl.springbootdemo.exception.InvalidEmployeeAgeException;
import com.oocl.springbootdemo.exception.SalaryNotPatchEmployeeAgeException;
import com.oocl.springbootdemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeDtoServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void should_not_create_employee_employee_given_invalid_age() {
        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setId(1);
        employeeDto1.setAge(17);
        employeeDto1.setSalary(25000);
        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto1.setId(2);
        employeeDto1.setAge(66);
        employeeDto1.setSalary(25000);
        assertThrows(InvalidEmployeeAgeException.class, () -> employeeService.createEmployee(employeeDto1));
        assertThrows(InvalidEmployeeAgeException.class, () -> employeeService.createEmployee(employeeDto2));
    }

    @Test
    public void should_not_create_employee_given_age_over_30_and_salary_below_20000() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setName("Tom");
        employeeDto.setAge(31);
        employeeDto.setGender("Male");
        employeeDto.setSalary(2222);

        assertThrows(SalaryNotPatchEmployeeAgeException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void should_create_an_active_status_employee_given_false_status_employee() {
        Employee expectEmployee = new Employee();
        expectEmployee.setId(1);
        expectEmployee.setName("Tom");
        expectEmployee.setAge(31);
        expectEmployee.setGender("Male");
        expectEmployee.setSalary(35000);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("Tom");
        employeeDto.setAge(31);
        employeeDto.setGender("Male");
        employeeDto.setSalary(35000);
        when(employeeRepository.create(any(Employee.class))).thenReturn(expectEmployee);
        Employee foundEmployee = employeeService.createEmployee(employeeDto);
        assertTrue(foundEmployee.getStatus());
    }

    @Test
    public void should_return_employee_given_exist_employee_id() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Tom");
        employee.setAge(20);
        employee.setGender("Male");
        employee.setSalary(1000);

        when(employeeRepository.findById(1)).thenReturn(employee);
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

        when(employeeRepository.findById(1)).thenReturn(employee);
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(999));
        verify(employeeRepository, times(1)).findById(999);
    }

    @Test
    public void should_set_employee_status_to_false_given_exist_employee_id_when_delete() {
        Employee beforeDeleteEmployee = new Employee();
        beforeDeleteEmployee.setId(1);
        beforeDeleteEmployee.setName("Tom");
        beforeDeleteEmployee.setAge(20);
        beforeDeleteEmployee.setGender("Male");
        beforeDeleteEmployee.setSalary(1000);
        beforeDeleteEmployee.setStatus(true);

        Employee afterDeleteEmployee = new Employee();
        afterDeleteEmployee.setId(1);
        afterDeleteEmployee.setName("Tom");
        afterDeleteEmployee.setAge(20);
        afterDeleteEmployee.setGender("Male");
        afterDeleteEmployee.setSalary(1000);
        afterDeleteEmployee.setStatus(false);

        when(employeeRepository.delete(beforeDeleteEmployee)).thenReturn(afterDeleteEmployee);
        when(employeeRepository.findById(1)).thenReturn(beforeDeleteEmployee);
        Employee deleteEmployee = employeeService.deleteEmployeeById(1);
        assertFalse(deleteEmployee.getStatus());
        verify(employeeRepository, times(1)).delete(beforeDeleteEmployee);
    }
}
