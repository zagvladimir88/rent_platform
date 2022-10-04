package com.zagvladimir.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagvladimir.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ItemRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Test
    @Rollback(value = false)
    void findAllItems() throws Exception {
        this.mockMvc
                .perform(get("/api/items/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath(
                                "$.[*].id",
                                containsInAnyOrder(2, 3, 4, 5,6,8)));
    }

    @Test
    @Rollback(value = false)
    void findAllItemsWithParams() throws Exception {
        this.mockMvc
                .perform(get("/api/items/").param("page", "0").param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id",containsInAnyOrder(2, 3, 4, 5,6,8)));
    }

    @Test
    @Rollback(value = false)
    void findByItemId() throws Exception {
        int itemId = 3;
        this.mockMvc
                .perform(get("/api/items/{id}",itemId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath(
                                "$.item.id",is(itemId)));
    }

    @Test
    void givenNoSuchElementException_whenGetItemsWithNonExistId() throws Exception {
        int itemId = 145665565;
        this.mockMvc
                .perform(get("/api/items/{id}",itemId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException))
                .andExpect(result -> assertEquals("No value present",result.getResolvedException().getMessage()));
    }

    @Test
    void givenNumberFormatException_whenGetItemsWithIncorrectId() throws Exception {
        String itemId = "asd";
        this.mockMvc
                .perform(get("/api/items/{id}",itemId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NumberFormatException))
                .andExpect(result -> assertEquals("For input string: \"asd\"",result.getResolvedException().getMessage()));
    }

    @Test
    void createItem() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("itemName", "Makita hr2470ft-TEST");
        body.put("itemTypeId",5);
        body.put("locationId",2);
        body.put("itemLocation","Microdistrict 17");
        body.put("description","Rotary Hammer makita hr2470ft");
        body.put("pricePerHour",6.0);
        body.put("available",true);
        body.put("status","ACTIVE");
        body.put("ownerId",4);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/items/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.item",is("Makita hr2470ft-TEST")));
    }

    @Test
    void deleteItemsById() throws Exception {
        int itemId = 4;
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/items/{itemId}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}