package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyService companyService;
    @BeforeEach
    void setUp() {
        companyService.clearCompanies();
    }
    @Test
    void should_create_company_when_post_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "Java"
                }
                """;
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void should_get_company_by_valid_id_when_get_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "Java"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn();

        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java"));
    }
    @Test
    void should_get_404_by_invalid_id_when_get_given_none() throws Exception {
        mockMvc.perform(get("/companies/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_update_company_by_id_when_put_given_a_valid_update_body() throws Exception {
        String createRequestBody = """
                {
                    "name": "Java"
                }
                """;
        String updateRequestBody = """
                {
                    "name": "C++"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestBody))
                .andReturn();

        mockMvc.perform(put("/companies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("C++"));
        mockMvc.perform(put("/companies/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_company_by_id_when_delete_given_a_valid_body() throws Exception {
        String requestBody1 = """
                {
                    "name": "Java"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1))
                .andReturn();

        mockMvc.perform(delete("/companies/2"))
                .andExpect(status().isNoContent())
                .andReturn();
        mockMvc.perform(delete("/companies/1"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void should_get_companies_with_pagination_when_get_given_10_valid_bodies() throws Exception {
        for (int i = 1; i <= 10; i++) {
            String requestBody = String.format("""
            {
                "name": "Company %d"
            }
            """, i);

            mockMvc.perform(post("/companies")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andReturn();
        }
        mockMvc.perform(get("/companies?page=1&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.totalItems").value(10))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.content[0].name").value("Company 1"));
        mockMvc.perform(get("/companies?page=2&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.totalItems").value(10))
                .andExpect(jsonPath("$.currentPage").value(2))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.content[0].name").value("Company 6"));
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.pageSize").value(5));
    }
}
