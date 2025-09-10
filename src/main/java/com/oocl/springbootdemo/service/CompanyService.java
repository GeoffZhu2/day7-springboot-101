package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Company;
import com.oocl.springbootdemo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        return companyRepository.create(company);
    }

    public void clearCompanies() {
        companyRepository.clearAll();
    }

    public Company getCompanyById(int id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        return companyOptional.orElse(null);
    }

    public Map<String, Object> getCompanies(int page, int size) {
        List<Company> companies = companyRepository.findAll();
        int totalItems = companies.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int fromIndex = (page - 1) * size;
        if (fromIndex > totalItems) {
            Map<String, Object> response = new HashMap<>();
            response.put("content", List.of());
            response.put("totalPages", totalPages);
            response.put("totalItems", totalItems);
            response.put("currentPage", page);
            response.put("pageSize", size);
            return response;
        }
        int toIndex = Math.min(fromIndex + size, totalItems);

        List<Company> pagedCompanies = companies.subList(fromIndex, toIndex);

        Map<String, Object> response = new HashMap<>();
        response.put("content", pagedCompanies);
        response.put("totalPages", totalPages);
        response.put("totalItems", totalItems);
        response.put("currentPage", page);
        response.put("pageSize", size);
        return response;
    }

    public Company updateCompanyById(Company company, int id) {
        return companyRepository.update(company, id);
    }

    public void deleteCompanyById(int id) {
        companyRepository.delete(id);
    }
}
