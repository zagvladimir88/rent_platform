package com.zagvladimir.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import com.zagvladimir.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
class ItemControllerTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired UserService userService;

  @Test
  void findAllItems() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/items/")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andExpect(jsonPath("$.content[0].itemName").value("Makita hr2470ft"));
  }

  @Test
  void findByItemId() throws Exception {
    int itemId = 3;
    this.mockMvc
        .perform(get("/api/items/{id}", itemId))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.item.id", is(itemId)));
  }

  @Test
  void givenEntityNotFoundException_whenGetItemsWithNonExistId() throws Exception {
    int itemId = 145665565;
    this.mockMvc
        .perform(get("/api/items/{id}", itemId))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
  }

  @Test
  void givenNumberFormatException_whenGetItemsWithIncorrectId() throws Exception {
    String itemId = "asd";
    this.mockMvc
        .perform(get("/api/items/{id}", itemId))
        .andDo(print())
        .andExpect(status().is5xxServerError())
        .andExpect(
            result ->
                assertTrue(
                    result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
  }

  @Test
  @WithMockUser(username="admin",roles={"ADMIN"})
  void createItem() throws Exception {
//    Map<String, Object> body = new HashMap<>();
//    body.put("itemName", "Makita hr2470ft-TEST");
//    body.put("subCategoryId", 5);
//    body.put("brand", "Makita");
//    body.put("description", "Rotary Hammer makita hr2470ft");
//    body.put("pricePerDay", 6.0);
//    body.put("available", true);
//    body.put("status", "ACTIVE");
    Map<?, ?> map =
            objectMapper.readValue(
                    Paths.get("src/test/resources/json_for_test/itemCreate.json").toFile(), Map.class);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map))
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.itemName", is("Makita hr2470ft-TEST")))
        .andExpect(jsonPath("$.brand", is("Makita")));
  }
}
