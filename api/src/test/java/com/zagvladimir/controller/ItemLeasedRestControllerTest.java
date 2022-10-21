package com.zagvladimir.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ItemLeasedRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllItems() throws Exception {
        this.mockMvc
                .perform(get("/api/items-leased/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath(
                                "$", notNullValue()));
    }

    @Test
    void createItem() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("itemId","3");
        body.put("renterId","6");
        body.put("timeFrom","2022-09-06T13:20:05.000+00:00");
        body.put("timeTo","2022-09-06T15:20:11.000+00:00");
        body.put("pricePerHour","9.0");
        body.put("discount","1");
        body.put("fee","9");
        body.put("priceTotal","9");
        body.put("rentierGradeDescription","TEST");
        body.put("renterGradeDescription","TEST");
        body.put("status","ACTIVE");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/items-leased/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath(
                                "$.rentierGradeDescription").value("TEST"))
                .andExpect(
                        jsonPath(
                                "$.renterGradeDescription").value("TEST"));

    }

    @Test
    void findItemLeasedById() throws Exception {
        Long id = 1L;
        this.mockMvc
                .perform(get("/api/items-leased/{id}",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath(
                                "$.item.itemId").value("2"));
    }

    @Test
    void deleteItemLeasedById() throws Exception {
        Long id = 1L;
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/items-leased/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}