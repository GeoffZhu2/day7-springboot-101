package com.oocl.springbootdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SpringbootDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_create_employee_when_post_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 35,
                    "gender": "Male",
                    "salary": 15000
                }
                """;
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(35))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(15000));
    }

    @Test
    void should_get_employee_by_valid_id_when_get_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 35,
                    "gender": "Male",
                    "salary": 15000
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
                .andExpect(jsonPath("$.salary").value(15000));
    }
    @Test
    void should_get_employee_by_invalid_id_when_get_given_a_valid_body() throws Exception {
        // 测试不存在的 ID
        mockMvc.perform(get("/employees/999"))
                .andExpect(status().isNotFound());
    }
}
