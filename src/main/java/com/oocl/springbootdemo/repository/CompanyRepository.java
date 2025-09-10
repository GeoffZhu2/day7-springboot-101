package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CompanyRepository {
    private final List<Company> companies = new ArrayList<>();
    private static int companyId = 0;

    public Company create(Company company) {
        company.setId(++companyId);
        companies.add(company);
        return company;
    }

    public void clearAll() {
        companies.clear();
        companyId = 0;
    }

    public Optional<Company> findById(int id) {
        return companies.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    public List<Company> findAll() {
        return companies;
    }

    public Company update(Company company, int id) {
        Optional<Company> companyOptional = findById(id);
        if(companyOptional.isPresent()) {
            Company updatedCompany = companyOptional.get();
            updatedCompany.setName(company.getName());
            return updatedCompany;
        }
        return null;
    }

    public void delete(int id) {
        companies.removeIf(findCompany -> findCompany.getId() == id);
    }
}
