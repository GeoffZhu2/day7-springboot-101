package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.Company;
import com.oocl.springbootdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable int id) {
        return companyService.getCompanyById(id);
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
