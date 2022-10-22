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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
class CountryControllerTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void findAllCountries() throws Exception {
    this.mockMvc
        .perform(get("/api/countries/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            jsonPath(
                "$.content[*].countryName", containsInAnyOrder("Belarus", "Russia", "Germany")));
  }

  @Test
  void findCountryById() throws Exception {
    Long id = 1L;
    this.mockMvc
        .perform(get("/api/countries/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.country.countryName").value("Belarus"));
  }

  @Test
  void createCountry() throws Exception {
    Map<String, Object> body = new HashMap<>();
    body.put("countryName", "CountryTest");
    body.put("status", "ACTIVE");

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/countries/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.[*].countryName", hasItem("CountryTest")));
  }

  @Test
  void deleteCountryById() throws Exception {
    Long id = 1L;
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/countries/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void updateCountry() throws Exception {
    Map<String, Object> body = new HashMap<>();
    body.put("status", "ACTIVE");
    body.put("countryName", "TEST");

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/countries/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.countryName").value("TEST"));
  }
}
