package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    public Company findById(int id) {
        return companies.stream().filter(company -> company.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Company> findAll() {
        return companies;
    }

    public Company update(Company company) {
        company.setName(company.getName());
        return company;
    }

    public void delete(int id) {
        companies.removeIf(findCompany -> findCompany.getId() == id);
    }
}
