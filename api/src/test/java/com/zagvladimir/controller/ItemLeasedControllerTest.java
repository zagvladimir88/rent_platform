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
class ItemLeasedControllerTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void findAllItems() throws Exception {
    this.mockMvc
        .perform(get("/api/items-leased/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()));
  }

  @Test
  void createItem() throws Exception {
    Map<String, Object> body = new HashMap<>();
    body.put("itemId", "3");
    body.put("renterId", "6");
    body.put("timeFrom", "2022-09-06T13:20:05.000+00:00");
    body.put("timeTo", "2022-09-06T15:20:11.000+00:00");
    body.put("pricePerDay", "9.0");
    body.put("discount", "1");
    body.put("priceTotal", "9");
    body.put("status", "ACTIVE");

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/items-leased/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.renterId").value("6"))
        .andExpect(jsonPath("$.timeFrom").value("2022-09-06T13:20:05.000+00:00"));
  }

  @Test
  void findItemLeasedById() throws Exception {
    Long id = 1L;
    this.mockMvc
        .perform(get("/api/items-leased/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.itemLeased.itemId").value("2"));
  }

  @Test
  void deleteItemLeasedById() throws Exception {
    Long id = 1L;
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/items-leased/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
