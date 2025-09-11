package com.oocl.springbootdemo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private long id;
    private String name;
    private String gender;
    private int age;
    private double salary;
    private Long companyId;
}
