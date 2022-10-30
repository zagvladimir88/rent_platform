package com.zagvladimir.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Paths;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
class GradeControllerTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void findAllGrades() throws Exception {
    this.mockMvc
        .perform(get("/api/grades/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()));
  }

  @Test
  void findlGradeById() throws Exception {
    Long id = 1L;
    this.mockMvc
        .perform(get("/api/grades/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.Grade.itemId").value("2"));
  }

  @Test
  void createGrade() throws Exception {
    Map<?, ?> map =
        objectMapper.readValue(
            Paths.get("src/test/resources/json_for_test/gradeCreate.json").toFile(), Map.class);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/grades/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.grade.description").value("TEST"))
        .andExpect(jsonPath("$.grade.grade").value("4.0"));
  }

  @Test
  void softDeleteGradeById() throws Exception {
    Long gradeId = 8L;
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/api/grades/{id}", gradeId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
