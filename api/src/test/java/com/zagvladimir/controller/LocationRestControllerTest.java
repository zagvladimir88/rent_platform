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
class LocationRestControllerTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void findAllLocations() throws Exception {
    this.mockMvc
        .perform(get("/api/locations/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()));
  }

  @Test
  void findLocationById() throws Exception {
    Long id = 1L;
    this.mockMvc
        .perform(get("/api/locations/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.Location.postalCode").value("246028"));
  }

  @Test
  void createLocation() throws Exception {
    Map<String, Object> body = new HashMap<>();
    body.put("postalCode", "33333");
    body.put("name", "TEST");
    body.put("description", "TEST");
    body.put("countryId", "1");
    body.put("status", "ACTIVE");

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/locations/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.postalCode").value("33333"))
        .andExpect(jsonPath("$.name").value("TEST"));
  }

  @Test
  void deleteLocationById() throws Exception {
    Long id = 1L;
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/locations/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void updateUser() throws Exception {
    Map<String, Object> body = new HashMap<>();
    body.put("postalCode", "33333");
    body.put("name", "TEST");
    body.put("description", "TEST");
    body.put("countryId", "1");
    body.put("status", "ACTIVE");

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/locations/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.postalCode").value("33333"))
        .andExpect(jsonPath("$.name").value("TEST"));
  }
}
