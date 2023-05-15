package com.zagvladimir.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Paths;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
class CategoryControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllItemCategories() throws Exception {
        this.mockMvc
                .perform(get("/api/item-categories/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", notNullValue()));
    }

    @Test
    void findItemCategoryById() throws Exception {
        Long id = 1L;
        this.mockMvc
                .perform(get("/api/item-categories/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName").value("TEST1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createItemCategory() throws Exception {
        Map<?, ?> map =
                objectMapper.readValue(
                        Paths.get("src/test/resources/json_for_test/categoryCreate.json").toFile(), Map.class);

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/item-categories/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(map))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryName").value("TEST"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteItemCategoryById() throws Exception {
        Long id = 1L;
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/item-categories/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
