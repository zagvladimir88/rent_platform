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
  void findAllItemsLeased() throws Exception {
    this.mockMvc
        .perform(get("/api/items-leased/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()));
  }

  @Test
  @WithMockUser(username="admin",roles={"ADMIN"})
  void createItem() throws Exception {
    Map<?, ?> map =
        objectMapper.readValue(
            Paths.get("src/test/resources/json_for_test/itemLeasedCreate.json").toFile(),
            Map.class);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/items-leased/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map))
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
}
