package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.Company;
import com.oocl.springbootdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(company));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> queryCompanyById(@PathVariable int id) {
        Optional<Company> company = companyService.queryCompanyById(id);
        return company.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCompanies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return companyService.getCompanies(page, size);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompanyById(@RequestBody Company company, @PathVariable int id) {
        return companyService.updateCompanyById(company, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Company> deleteCompanyById(@PathVariable int id) {
        return companyService.deleteCompanyById(id);
    }

}
