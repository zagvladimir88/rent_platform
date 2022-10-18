package com.zagvladimir.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SubItemTypeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Rollback(value = false)
    void findAllISubItemTypes() throws Exception {
        this.mockMvc
                .perform(get("/api/sub-item-types/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath(
                                "$", notNullValue()));
    }

    @Test
    @Rollback(value = false)
    void findSubItemTypeById() throws Exception {
        Long id = 1L;
        this.mockMvc
                .perform(get("/api/sub-item-types/{id}",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath(
                                "$.subCategoryName").value("Дрели"));
    }

    @Test
    void createSubItemType() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("subCategoryName","TEST");
        body.put("categoryId","2");
        body.put("status","ACTIVE");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/sub-item-types/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath(
                                "$.subCategoryName").value("TEST"));
    }

    @Test
    void deleteSubItemTypeById() throws Exception {
        Long id = 1L;
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/sub-item-types/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}