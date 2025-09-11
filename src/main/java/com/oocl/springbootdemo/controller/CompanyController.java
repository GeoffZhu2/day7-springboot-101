package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.dto.CompanyDto;
import com.oocl.springbootdemo.entity.Company;
import com.oocl.springbootdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDto companyDto) {
        Company createdCompany = companyService.createCompany(companyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable long id) {
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(company);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCompanies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        Map<String, Object> response = companyService.getCompanies(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompanyById(@RequestBody CompanyDto companyDto, @PathVariable long id) {
        Company updatedCompany = companyService.updateCompanyById(companyDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable long id) {
        companyService.deleteCompanyById(id);
        return ResponseEntity.noContent().build();
    }
}
