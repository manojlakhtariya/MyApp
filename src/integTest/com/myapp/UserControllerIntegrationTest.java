package com.myapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.error.RecordNotFoundException;
import com.myapp.model.UserRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIntegrationTest extends AbstractContainerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void createUserTest() throws Exception {
        UserRequest userRequest = new UserRequest("Jai", "lakhtariya", "tes@gmail.com", "774-253-5000");

        String requestAsString = asJsonString(userRequest);

        mvc.perform( MockMvcRequestBuilders
                .post("/api/users")
                .content(requestAsString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jai"))
                .andExpect((MockMvcResultMatchers.jsonPath("$.lastName").value("lakhtariya")));

    }

    @Test
    public void findByIdExceptionTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/users/{user_id}", 33)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result -> assertEquals("Invalid employee id:"+33, result.getResolvedException().getMessage()));
    }
}
