package com.zagvladimir.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Paths;
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
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  void findAllUsers() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/users/").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['pageable']['paged']").value("true"))
        .andExpect(jsonPath("$.content[0].firstName").value("Evgenii"));
  }

  @Test
  @WithMockUser(
      username = "Bukowski",
      roles = {"USER"})
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
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  void findByLogin() throws Exception {
    this.mockMvc
        .perform(get("/api/users/login/strjke"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.credentials.userLogin").value("strjke"));
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  void updateUser() throws Exception {
    Map<?, ?> map =
        objectMapper.readValue(
            Paths.get("src/test/resources/json_for_test/userCreate.json").toFile(), Map.class);
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.firstName").value("Vladimir"))
        .andExpect(jsonPath("$.user.lastName").value("Ivanov"))
        .andExpect(jsonPath("$.user.credentials.userLogin").value("JsonTestLogin"));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  void softDeleteUsersById() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/api/users/delete/{2}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  void userChangeCredentials() throws Exception {
    Map<?, ?> map =
        objectMapper.readValue(
            Paths.get("src/test/resources/json_for_test/userChangeCredentials.json").toFile(), Map.class);
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/api/users/2/change-credential")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.credentials.userLogin").value("TEST_NEW_LOGIN"));
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  void userChangeAddress() throws Exception {
    Map<?, ?> map =
        objectMapper.readValue(
            Paths.get("src/test/resources/json_for_test/userChangeAddess.json").toFile(), Map.class);
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/api/users/2/change-address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.address.addressLine1").value("Microdistrict 20"))
        .andExpect(jsonPath("$.user.address.addressLine2").value("House 20-83"))
        .andExpect(jsonPath("$.user.address.city").value("Zhlobin"))
        .andExpect(jsonPath("$.user.address.postalCode").value("247210"));
  }
}
