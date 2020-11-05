package com.myapp;

import com.myapp.error.RecordNotFoundException;
import com.myapp.model.UserRequest;
import org.junit.jupiter.api.Test;
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
        UserRequest userRequest = new UserRequest("Bob", "Phillip", "tes@gmail.com", "774-253-5000");

        String requestAsString = asJsonString(userRequest);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/users")
                .content(requestAsString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Bob"))
                .andExpect((MockMvcResultMatchers.jsonPath("$.lastName").value("Phillip")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phonenumber").value("774-253-5000"));

    }

    @Test
    public void findByIdExceptionTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/users/{user_id}", 33)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result -> assertEquals("Unable to find User with id " + 33, result.getResolvedException().getMessage()));
    }

    @Test
    public void findByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/users/{user_id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

//    @Test
//    public void findAllTest() {
//        mvc.perform(MockMvcRequestBuilders
//                .get("/api/users")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
//    }
}
