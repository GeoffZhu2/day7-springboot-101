package com.oocl.springbootdemo.repository.dao;

import com.oocl.springbootdemo.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, Long> {
}
