package com.zagvladimir.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
class SubCategoryControllerTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void findAllISubItemTypes() throws Exception {
    this.mockMvc
        .perform(get("/api/sub-item-types/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()));
  }

  @Test
  void findSubItemTypeById() throws Exception {
    Long id = 1L;
    this.mockMvc
        .perform(get("/api/sub-item-types/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.subCategoryName").value("TEST1"));
  }

  @Test
  void createSubItemType() throws Exception {
    Map<String, Object> body = new HashMap<>();
    body.put("subCategoryName", "TEST");
    body.put("categoryId", "2");
    body.put("status", "ACTIVE");

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/sub-item-types/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.subCategoryName").value("TEST"));
  }

  @Test
  void deleteSubItemTypeById() throws Exception {
    Long id = 1L;
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/sub-item-types/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
