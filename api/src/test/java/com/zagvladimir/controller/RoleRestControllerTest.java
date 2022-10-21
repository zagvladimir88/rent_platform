package com.zagvladimir.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
class RoleRestControllerTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void findAllRoles() throws Exception {
    this.mockMvc
        .perform(get("/api/roles/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            jsonPath(
                "$.result[*].name",
                containsInAnyOrder("ROLE_USER", "ROLE_ADMIN", "ROLE_ANONYMOUS", "ROLE_MODERATOR")));
  }

  @Test
  void findRolesByUserId() throws Exception {
    this.mockMvc
            .perform(get("/api/roles/users/4"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(
                    jsonPath(
                            "$.result[*].name",hasItem("ROLE_ADMIN")));
  }

  @Test
  void findRoleById() throws Exception {
    this.mockMvc
            .perform(get("/api/roles/4"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(
                    jsonPath(
                            "$.role.name",allOf(startsWith("ROLE_"))));
  }

  @Test
  void createRole() throws Exception {
    Map<String,Object> body = new HashMap<>();
    body.put("name","ROLE_TEST");
    body.put("status","ACTIVE");

    mockMvc.perform(MockMvcRequestBuilders.post("/api/roles/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.[*].name",hasItem("ROLE_ADMIN")));
  }

  @Test
  void deleteRoleById() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders
                    .delete("/api/roles/{2}", "2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
  }
}
