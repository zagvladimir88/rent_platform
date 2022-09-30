package com.zagvladimir;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.yml")
@Sql(value = {"/create-location-after.sql","/create-user-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/create-locations-before.sql","/create-user-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SpringBootStarterTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void getAllUsersTest() throws Exception {
    this.mockMvc
        .perform(get("/api/users/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result[0].id").value("1"))
        .andExpect(jsonPath("$.result[1].id").value("2"));
  }

  @Test
  public void findUserByLoginTest() throws Exception {
    this.mockMvc
        .perform(get("/api/users/login/strjke"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.userLogin").value("strjke"));
  }

  @Test
  public void createNewUserTest() throws Exception {
    Map<String,Object> body = new HashMap<>();
    body.put("status","ACTIVE");
    body.put("username","Evgenii");
    body.put("userLogin","joniq");
    body.put("userPassword","5555");
    body.put("locationId", "1");
    body.put("locationDetails","20 30 5");
    body.put("phoneNumber","802333324523");
    body.put("mobileNumber","+375256145343");
    body.put("email","evgeniiArgs@gmail.com");

    mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
  }

  @Test
  public void updateUserTest() throws Exception {
    Map<String,Object> body = new HashMap<>();
    body.put("status","ACTIVE");
    body.put("username","Evgenii");
    body.put("userLogin","joniq");
    body.put("userPassword","5555");
    body.put("locationId", "1");
    body.put("locationDetails","20 30 5");
    body.put("phoneNumber","802333324523");
    body.put("mobileNumber","+375256145343");
    body.put("email","evgeniiArgs@gmail.com");

    mockMvc.perform(MockMvcRequestBuilders.put("/api/users/2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user.username").value("Evgenii"));
  }

  @Test
  public void getUserByIdTest() throws Exception {
    this.mockMvc
            .perform(get("/api/users/2"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user.id").value("2"));
  }

  @Test
  public void deleteUserByIdTest() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders
                    .delete("/api/users/{2}", "2")
                    .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  public void getAllUsersWithParamsTest() throws Exception {
    this.mockMvc
            .perform(get("/api/users/")
                    .param("limit","10")
                    .param("offset","0"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result[0].id").value("1"))
            .andExpect(jsonPath("$.result[1].id").value("2"));
  }

}
