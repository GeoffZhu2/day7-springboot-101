package com.oocl.springbootdemo.cpmpany;

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

    List<Company> companies = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        company.setId(companies.size() + 1);
        companies.add(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> queryCompanyById(@PathVariable int id) {
        Optional<Company> company = companies.stream()
                .filter(e -> e.getId() == id)
                .findFirst();

        return company.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCompanies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
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

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompanyById(@RequestBody Company company, @PathVariable int id) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Company> deleteCompanyById(@PathVariable int id) {
        for (Company findCompany : companies) {
            if(findCompany.getId() == id) {
                companies.remove(findCompany);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.noContent().build();
    }

    public void clearCompanies() {
        companies.clear();
    }
}
