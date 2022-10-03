package com.zagvladimir.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Sql(
    value = {"/create-location-after.sql", "/create-user-after.sql"},
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(
    value = {"/create-locations-before.sql", "/create-user-before.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserRestControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void findAllUsers() throws Exception {
    this.mockMvc
        .perform(get("/api/users/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result[0].id").value("1"))
        .andExpect(jsonPath("$.result[1].id").value("2"));
  }

  @Test
  void findAllUsersWithParams() throws Exception {
    this.mockMvc
        .perform(get("/api/users/").param("limit", "10").param("offset", "0"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result[0].id").value("1"))
        .andExpect(jsonPath("$.result[1].id").value("2"));
  }

  @Test
  void findUserById() throws Exception {
    int id;

    for (int i = 1; i < 3; i++) {
      id = i;
      this.mockMvc
          .perform(get("/api/users/{id}", id))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.user.id").value(id));
    }
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
  void createUser() throws Exception {
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
            MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  void conflictWhenCreateUser() throws Exception {
    Map<String, Object> body = new HashMap<>();
    body.put("status", "ACTIVE");
    body.put("username", "Vladimir");
    body.put("userLogin", "strjke");
    body.put("userPassword", "5555");
    body.put("locationId", "1");
    body.put("locationDetails", "20 30 5");
    body.put("phoneNumber", "802333324523");
    body.put("mobileNumber", "+375256145343");
    body.put("email", "strjke@gmail.com");

    mockMvc
            .perform(
                    MockMvcRequestBuilders.post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body))
                            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isConflict());
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
        .andExpect(jsonPath("$.user.username").value("Evgenii"));
  }
}
