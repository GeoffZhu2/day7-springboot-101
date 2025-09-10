package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CompanyService {

    List<Company> companies = new ArrayList<>();
    static int companyId = 0;

    public Company createCompany(Company company) {
        company.setId(++companyId);
        companies.add(company);
        return company;
    }
    public void clearCompanies() {
        companies.clear();
        companyId = 0;
    }

    public Optional<Company> queryCompanyById(int id) {
        return companies.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    public ResponseEntity<Map<String, Object>> getCompanies(int page, int size) {
        List<Company> pageCompanies = companies;
        int totalItems = pageCompanies.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, totalItems);
        if (fromIndex > totalItems) {
            return ResponseEntity.ok(Map.of(
                    "content", pageCompanies,
                    "totalPages", totalPages,
                    "totalItems", totalItems,
                    "currentPage", page,
                    "pageSize", size
            ));
        }
        List<Company> pagedCompanies = pageCompanies.subList(fromIndex, toIndex);

        return ResponseEntity.ok(Map.of(
                "content", pagedCompanies,
                "totalPages", totalPages,
                "totalItems", totalItems,
                "currentPage", page,
                "pageSize", size
        ));
    }

    public ResponseEntity<Company> updateCompanyById(Company company, int id) {
        Optional<Company> companyOptional = companies.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        if(companyOptional.isPresent()) {
            Company updatedCompany = companyOptional.get();
            updatedCompany.setName(company.getName());
            return ResponseEntity.ok(updatedCompany);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Company> deleteCompanyById(int id) {
        for (Company findCompany : companies) {
            if(findCompany.getId() == id) {
                companies.remove(findCompany);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.noContent().build();
    }
}
