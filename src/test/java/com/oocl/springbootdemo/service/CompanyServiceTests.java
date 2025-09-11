package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.entity.Company;
import com.oocl.springbootdemo.exception.CompanyNotFoundException;
import com.oocl.springbootdemo.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CompanyServiceTests {
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;
    @Test
    public void should_return_company_given_exist_company_id() {
        Company company = new Company(1, "Apple");

        when(companyRepository.findById(1)).thenReturn(company);
        Company foundEmployee = companyService.getCompanyById(1);
        assertEquals(company, foundEmployee);
        verify(companyRepository, times(1)).findById(1);
    }

    @Test
    public void should_return_null_given_not_exist_company_id() {
        Company company = new Company(1, "Apple");

        when(companyRepository.findById(1)).thenReturn(company);
        assertThrows(CompanyNotFoundException.class, () -> companyService.getCompanyById(9999));
        verify(companyRepository, times(1)).findById(9999);
    }

}
