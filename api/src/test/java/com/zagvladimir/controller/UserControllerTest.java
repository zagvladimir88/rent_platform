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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andExpect(jsonPath("$.content[0].firstName").value("Oleg"));
  }

  @Test
  @WithMockUser(username="Bukowski",roles={"USER"})
  void findUserById() throws Exception {
    int id = 39;

    this.mockMvc
        .perform(get("/api/users/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.id").value(id))
        .andExpect(jsonPath("$.user.firstName").value("Charles"));
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
    body.put("firstName", "Evgenii");
    body.put("lastName", "Popov");
    body.put("userLogin", "joniq");
    body.put("userPassword", "5555");
    body.put("locationId", "1");
    body.put("locationDetails", "20 30 5");
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
        .andExpect(jsonPath("$.user.firstName").value("Evgenii"));
  }
}
