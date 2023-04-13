package com.zagvladimir.controller;

import com.zagvladimir.BaseIntegrationTest;
import com.zagvladimir.annotations.IT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@IT
class AdminControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void confirmItemBooking() {
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void deleteUsersById() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/admin/users/{2}", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void deleteSubCategoryById() throws Exception {
        Long id = 1L;
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/admin/sub-categories/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void deleteItemLeasedById() throws Exception {
        Long id = 1L;
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/admin/items-leased/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void deleteItemsById() throws Exception {
        int itemId = 4;
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/admin/items/{itemId}", itemId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void deleteGradeById() throws Exception {
        Long id = 1L;
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/admin/grades/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void deleteItemCategoryById() throws Exception {
        Long id = 1L;
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/admin/categories/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void deleteRoleById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/admin/roles/{2}", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}