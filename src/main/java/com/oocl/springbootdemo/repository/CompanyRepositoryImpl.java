package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.entity.Company;
import com.oocl.springbootdemo.repository.dao.CompanyJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {
    @Autowired
    private CompanyJpaRepository companyJpaRepository;

    public Company create(Company company) {
        return companyJpaRepository.save(company);
    }

    public void clearAll() {
        companyJpaRepository.deleteAll();
    }

    public Company findById(long id) {
        return companyJpaRepository.findById(id)
                .orElse(null);
    }

    public Company update(Company company) {
        return companyJpaRepository.save(company);
    }

    public void delete(long id) {
        companyJpaRepository.deleteById(id);
    }

    @Override
    public Page<Company> findWithPage(Pageable pageable) {
        return companyJpaRepository.findAll(pageable);
    }
}
