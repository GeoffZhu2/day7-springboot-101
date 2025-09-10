package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.service.EmployeeService;
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
class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;
    @BeforeEach
    void setUp() {
        employeeService.clearEmployees();
    }
    @Test
    void should_create_employee_when_post_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 35,
                    "gender": "Male",
                    "salary": 22000
                }
                """;
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(35))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(22000));
    }

    @Test
    void should_not_create_employee_when_post_given_a_valid_body() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 17,
                    "gender": "Male",
                    "salary": 15000
                }
                """;
        String requestBody2 = """
                {
                    "name": "John Smith",
                    "age": 66,
                    "gender": "Male",
                    "salary": 23000
                }
                """;
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_get_employee_by_valid_id_when_get_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 35,
                    "gender": "Male",
                    "salary": 25000
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn();

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(35))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(25000));
        mockMvc.perform(get("/employees/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_employee_by_gender_when_get_given_2_valid_bodies() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 35,
                    "gender": "Male",
                    "salary": 30000
                }
                """;
        String requestBody2 = """
                {
                    "name": "Tom Cat",
                    "age": 40,
                    "gender": "Female",
                    "salary": 22000
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1))
                .andReturn();
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2))
                .andReturn();

        mockMvc.perform(get("/employees?gender=Male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("John Smith"))
                .andExpect(jsonPath("$.content[0].age").value(35))
                .andExpect(jsonPath("$.content[0].gender").value("Male"))
                .andExpect(jsonPath("$.content[0].salary").value(30000))
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.pageSize").value(5));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("John Smith"))
                .andExpect(jsonPath("$.content[0].age").value(35))
                .andExpect(jsonPath("$.content[0].gender").value("Male"))
                .andExpect(jsonPath("$.content[0].salary").value(30000))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("Tom Cat"))
                .andExpect(jsonPath("$.content[1].age").value(40))
                .andExpect(jsonPath("$.content[1].gender").value("Female"))
                .andExpect(jsonPath("$.content[1].salary").value(22000))
                .andExpect(jsonPath("$.totalItems").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.pageSize").value(5));
    }

    @Test
    void should_get_employee_by_gender_when_get_given_a_valid_body() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 35,
                    "gender": "Male",
                    "salary": 15000
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1))
                .andReturn();

        mockMvc.perform(get("/employees?gender=Female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.totalItems").value(0))
                .andExpect(jsonPath("$.totalPages").value(0))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.pageSize").value(5));
    }

    @Test
    void should_update_employee_by_id_when_put_given_a_valid_update_body() throws Exception {
        String createRequestBody = """
                {
                    "name": "John Smith",
                    "age": 35,
                    "gender": "Male",
                    "salary": 30000
                }
                """;
        String updateRequestBody = """
                {
                    "name": "Tom Cat",
                    "age": 40,
                    "gender": "Female",
                    "salary": 30000
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestBody))
                .andReturn();

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tom Cat"))
                .andExpect(jsonPath("$.age").value(40))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(30000));
    }
    @Test
    void should_delete_employee_by_id_when_delete_given_a_valid_body() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 35,
                    "gender": "Male",
                    "salary": 15000
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1))
                .andReturn();

        mockMvc.perform(delete("/employees/2"))
                .andExpect(status().isNoContent())
                .andReturn();
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void should_get_employees_with_pagination_when_get_given_10_valid_bodies() throws Exception {
        for (int i = 1; i <= 10; i++) {
            String requestBody = String.format("""
            {
                "name": "Employee %d",
                "age": %d,
                "gender": "Male",
                "salary": %d
            }
            """, i, 20 + i, 10000 + i * 1000);

            mockMvc.perform(post("/employees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andReturn();
        }
        mockMvc.perform(get("/employees?page=1&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.totalItems").value(10))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.content[0].name").value("Employee 1"));
        mockMvc.perform(get("/employees?page=2&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.totalItems").value(10))
                .andExpect(jsonPath("$.currentPage").value(2))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.content[0].name").value("Employee 6"));
        mockMvc.perform(get("/employees?gender=Male&page=1&size=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.totalItems").value(10));
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.pageSize").value(5));
    }
}
