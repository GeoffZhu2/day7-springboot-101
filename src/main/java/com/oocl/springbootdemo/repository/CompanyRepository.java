package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.Company;

import java.util.List;

public interface CompanyRepository {

    Company create(Company company);

    void clearAll();

    Company findById(long id);

    List<Company> findAll();

    Company update(Company company);

    void delete(long id);
}
