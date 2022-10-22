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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
class UserControllerTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void findAllUsers() throws Exception {
    this.mockMvc
        .perform(get("/api/users/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result[*]").isNotEmpty());
  }

  @Test
  void findAllUsersWithParams() throws Exception {
    this.mockMvc
        .perform(get("/api/users/").param("page", "0").param("size", "10"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result[0].id").value("6"))
        .andExpect(jsonPath("$.result[1].id").value("5"));
  }

  @Test
  void findUserById() throws Exception {
    int id = 4;

    this.mockMvc
        .perform(get("/api/users/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.id").value(id))
        .andExpect(jsonPath("$.user.username").value("Vladimir"));
  }

  @Test
  void findByLogin() throws Exception {
    this.mockMvc
        .perform(get("/api/users/login/strjke"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.userLogin").value("strjke"));
  }

  @Test
  void deleteUsersById() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/users/{2}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void updateUser() throws Exception {
    Map<String, Object> body = new HashMap<>();
    body.put("status", "ACTIVE");
    body.put("username", "Evgenii");
    body.put("userLogin", "joniq");
    body.put("userPassword", "5555");
    body.put("locationId", "1");
    body.put("locationDetails", "20 30 5");
    body.put("phoneNumber", "802333324523");
    body.put("mobileNumber", "+375256145343");
    body.put("email", "evgeniiArgs@gmail.com");

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("Evgenii"));
  }
}
