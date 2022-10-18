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
class ItemCategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Rollback(value = false)
    void findAllItemCategories() throws Exception {
        this.mockMvc
                .perform(get("/api/item-categories/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath(
                                "$.result[*]",
                                notNullValue()));
    }

    @Test
    @Rollback(value = false)
    void findItemCategoryById() throws Exception {
        Long id = 1L;
        this.mockMvc
                .perform(get("/api/item-categories/{id}",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath(
                                "$.itemCategory.categoryName").value("Строительный инструмент"));
    }

    @Test
    void createItemCategory() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("categoryName","TEST");
        body.put("status","ACTIVE");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/item-categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath(
                                "$.categoryName").value("TEST"));
    }

    @Test
    void deleteItemCategoryById() throws Exception {
        Long id = 1L;
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/item-categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}