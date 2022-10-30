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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
class RoleControllerTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  @WithMockUser(
      username = "GigaChad",
      roles = {"ADMIN"})
  void findAllRoles() throws Exception {
    this.mockMvc
        .perform(get("/api/roles/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            jsonPath(
                "$.content[*].name",
                containsInAnyOrder("ROLE_USER", "ROLE_ADMIN", "ROLE_ANONYMOUS", "ROLE_MODERATOR")));
  }

  @Test
  @WithMockUser(
      username = "GigaChad",
      roles = {"ADMIN"})
  void findRolesByUserId() throws Exception {
    this.mockMvc
        .perform(get("/api/roles/users/4"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.roles[*].name", hasItem("ROLE_ADMIN")));
  }

  @Test
  @WithMockUser(
      username = "GigaChad",
      roles = {"ADMIN"})
  void findRoleById() throws Exception {
    this.mockMvc
        .perform(get("/api/roles/4"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.role.name", allOf(startsWith("ROLE_"))));
  }

  @Test
  @WithMockUser(
      username = "GigaChad",
      roles = {"ADMIN"})
  void createRole() throws Exception {
    Map<?, ?> map =
        objectMapper.readValue(
            Paths.get("src/test/resources/json_for_test/roleCreate.json").toFile(), Map.class);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/roles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("ROLE_TEST"));
  }
}
