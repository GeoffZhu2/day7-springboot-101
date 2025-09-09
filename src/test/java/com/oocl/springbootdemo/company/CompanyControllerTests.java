package com.oocl.springbootdemo.company;

import com.oocl.springbootdemo.cpmpany.CompanyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyController companyController;
    @BeforeEach
    void setUp() {
        if (companyController != null) {
            companyController.clearCompanies();
        }
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

//    @Test
//    void should_get_company_by_valid_id_when_get_given_a_valid_body() throws Exception {
//        String requestBody = """
//                {
//                    "name": "Java"
//                }
//                """;
//        mockMvc.perform(post("/companies")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andReturn();
//
//        mockMvc.perform(get("/companies/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Java"));
//    }
//    @Test
//    void should_get_404_by_invalid_id_when_get_given_none() throws Exception {
//        // 测试不存在的 ID
//        mockMvc.perform(get("/companies/999"))
//                .andExpect(status().isNotFound());
//    }
//    @Test
//    void should_get_company_by_gender_when_get_given_2_valid_bodies() throws Exception {
//        String requestBody1 = """
//                {
//                    "name": "John Smith",
//                    "age": 35,
//                    "gender": "Male",
//                    "salary": 15000
//                }
//                """;
//        String requestBody2 = """
//                {
//                    "name": "Tom Cat",
//                    "age": 40,
//                    "gender": "Female",
//                    "salary": 18000
//                }
//                """;
//        mockMvc.perform(post("/companies")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody1))
//                .andReturn();
//        mockMvc.perform(post("/companies")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody2))
//                .andReturn();
//
//        mockMvc.perform(get("/companies?gender=Male"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(1)))
//                .andExpect(jsonPath("$.content[0].id").value(1))
//                .andExpect(jsonPath("$.content[0].name").value("John Smith"))
//                .andExpect(jsonPath("$.content[0].age").value(35))
//                .andExpect(jsonPath("$.content[0].gender").value("Male"))
//                .andExpect(jsonPath("$.content[0].salary").value(15000))
//                .andExpect(jsonPath("$.totalItems").value(1))
//                .andExpect(jsonPath("$.totalPages").value(1))
//                .andExpect(jsonPath("$.currentPage").value(1))
//                .andExpect(jsonPath("$.pageSize").value(5));
//
//        mockMvc.perform(get("/companies"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(2)))
//                .andExpect(jsonPath("$.content[0].id").value(1))
//                .andExpect(jsonPath("$.content[0].name").value("John Smith"))
//                .andExpect(jsonPath("$.content[0].age").value(35))
//                .andExpect(jsonPath("$.content[0].gender").value("Male"))
//                .andExpect(jsonPath("$.content[0].salary").value(15000))
//                .andExpect(jsonPath("$.content[1].id").value(2))
//                .andExpect(jsonPath("$.content[1].name").value("Tom Cat"))
//                .andExpect(jsonPath("$.content[1].age").value(40))
//                .andExpect(jsonPath("$.content[1].gender").value("Female"))
//                .andExpect(jsonPath("$.content[1].salary").value(18000))
//                .andExpect(jsonPath("$.totalItems").value(2))
//                .andExpect(jsonPath("$.totalPages").value(1))
//                .andExpect(jsonPath("$.currentPage").value(1))
//                .andExpect(jsonPath("$.pageSize").value(5));
//    }
//
//    @Test
//    void should_get_company_by_gender_when_get_given_a_valid_body() throws Exception {
//        String requestBody1 = """
//                {
//                    "name": "John Smith",
//                    "age": 35,
//                    "gender": "Male",
//                    "salary": 15000
//                }
//                """;
//        mockMvc.perform(post("/companies")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody1))
//                .andReturn();
//
//        mockMvc.perform(get("/companies?gender=Female"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(0)))
//                .andExpect(jsonPath("$.totalItems").value(0))
//                .andExpect(jsonPath("$.totalPages").value(0))
//                .andExpect(jsonPath("$.currentPage").value(1))
//                .andExpect(jsonPath("$.pageSize").value(5));
//    }
//
//    @Test
//    void should_update_company_by_id_when_put_given_a_valid_update_body() throws Exception {
//        String createRequestBody = """
//                {
//                    "name": "John Smith",
//                    "age": 35,
//                    "gender": "Male",
//                    "salary": 15000
//                }
//                """;
//        String updateRequestBody = """
//                {
//                    "name": "Tom Cat",
//                    "age": 40,
//                    "gender": "Female",
//                    "salary": 18000
//                }
//                """;
//        mockMvc.perform(post("/companies")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(createRequestBody))
//                .andReturn();
//
//        mockMvc.perform(put("/companies/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updateRequestBody))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Tom Cat"))
//                .andExpect(jsonPath("$.age").value(40))
//                .andExpect(jsonPath("$.gender").value("Female"))
//                .andExpect(jsonPath("$.salary").value(18000));
//        mockMvc.perform(put("/companies/999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updateRequestBody))
//                .andExpect(status().isNotFound());
//    }
//    @Test
//    void should_delete_company_by_id_when_delete_given_a_valid_body() throws Exception {
//        String requestBody1 = """
//                {
//                    "name": "John Smith",
//                    "age": 35,
//                    "gender": "Male",
//                    "salary": 15000
//                }
//                """;
//        mockMvc.perform(post("/companies")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody1))
//                .andReturn();
//
//        mockMvc.perform(delete("/companies/2"))
//                .andExpect(status().isNoContent())
//                .andReturn();
//        mockMvc.perform(delete("/companies/1"))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//
//    @Test
//    void should_get_companies_with_pagination_when_get_given_10_valid_bodies() throws Exception {
//        for (int i = 1; i <= 10; i++) {
//            String requestBody = String.format("""
//            {
//                "name": "Company %d",
//                "age": %d,
//                "gender": "Male",
//                "salary": %d
//            }
//            """, i, 20 + i, 10000 + i * 1000);
//
//            mockMvc.perform(post("/companies")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(requestBody))
//                    .andReturn();
//        }
//        mockMvc.perform(get("/companies?page=1&size=5"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(5)))
//                .andExpect(jsonPath("$.totalPages").value(2))
//                .andExpect(jsonPath("$.totalItems").value(10))
//                .andExpect(jsonPath("$.currentPage").value(1))
//                .andExpect(jsonPath("$.pageSize").value(5))
//                .andExpect(jsonPath("$.content[0].name").value("Company 1"));
//        mockMvc.perform(get("/companies?page=2&size=5"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(5)))
//                .andExpect(jsonPath("$.totalPages").value(2))
//                .andExpect(jsonPath("$.totalItems").value(10))
//                .andExpect(jsonPath("$.currentPage").value(2))
//                .andExpect(jsonPath("$.pageSize").value(5))
//                .andExpect(jsonPath("$.content[0].name").value("Company 6"));
//        mockMvc.perform(get("/companies?gender=Male&page=1&size=3"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(3)))
//                .andExpect(jsonPath("$.totalItems").value(10));
//        mockMvc.perform(get("/companies"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.currentPage").value(1))
//                .andExpect(jsonPath("$.pageSize").value(5));
//    }
}
