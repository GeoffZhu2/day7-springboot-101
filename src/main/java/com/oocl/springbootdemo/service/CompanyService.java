package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.dto.CompanyDto;
import com.oocl.springbootdemo.entity.Company;
import com.oocl.springbootdemo.entity.Employee;
import com.oocl.springbootdemo.exception.CompanyNotFoundException;
import com.oocl.springbootdemo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(CompanyDto companyDto) {
        Company company = new Company();
        company.setName(companyDto.getName());
        return companyRepository.create(company);
    }

    public void clearCompanies() {
        companyRepository.clearAll();
    }

    public Company getCompanyById(long id) {
        Company foundCompany = companyRepository.findById(id);
        if (foundCompany == null) {
            throw new CompanyNotFoundException();
        }
        return foundCompany;
    }

    public Map<String, Object> getCompanies(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Company> companies = companyRepository.findWithPage(pageable);

        int totalItems = (int) companies.getTotalElements();
        int totalPages = companies.getTotalPages();

        return getCompanyResponse(companies.getContent(), page, size, totalPages, totalItems);
    }

    private Map<String, Object> getCompanyResponse(List<Company> companies, int page, int size, int totalPages, int totalItems) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", companies);
        response.put("totalPages", totalPages);
        response.put("totalItems", totalItems);
        response.put("currentPage", page);
        response.put("pageSize", size);
        return response;
    }

    public Company updateCompanyById(CompanyDto companyDto) {
        getCompanyById(companyDto.getId());
        Company company = new Company();
        company.setId(companyDto.getId());
        company.setName(companyDto.getName());
        return companyRepository.update(company);
    }

    public void deleteCompanyById(long id) {
        getCompanyById(id);
        companyRepository.delete(id);
    }
}
