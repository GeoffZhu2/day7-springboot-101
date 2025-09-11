package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyRepository {

    Company create(Company company);

    void clearAll();

    Company findById(long id);

    List<Company> findAll();

    Company update(Company company);

    void delete(long id);

    Page<Company> findWithPage(Pageable pageable);
}
