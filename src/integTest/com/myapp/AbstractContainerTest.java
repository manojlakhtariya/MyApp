package com.myapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myapp.AbstractContainerTest.ContainerResourceConfiguration;
import com.myapp.domain.User;
import com.myapp.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("integration_test")
//@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ContainerResourceConfiguration.class)
@AutoConfigureMockMvc
public abstract class AbstractContainerTest {

    private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("spring_app_db")
            .withExposedPorts(3306);
    public static ObjectMapper objectMapper;
    protected static Long organizerId;

    static {
        mysql.start();
        System.setProperty("spring.datasource.username", mysql.getUsername());
        System.setProperty("spring.datasource.password", mysql.getPassword());
        System.setProperty("spring.datasource.url", mysql.getJdbcUrl());
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

    }

    protected Set<Long> attendeeIds;

    public static String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @TestConfiguration
    static class ContainerResourceConfiguration {

        @Autowired
        MockMvc mockMvc;

        @Bean
        public void testUsers() throws Exception {
            UserRequest userRequest1 = new UserRequest("Jai", "lakhtariya", "a@gmail.com", "774-253-5000");
            UserRequest userRequest2 = new UserRequest("Manoj", "lakhtariya", "b@gmail.com", "774-253-5555");
            UserRequest userRequest3 = new UserRequest("Ankit", "lakhtariya", "c@gmail.com", "774-253-8888");
            UserRequest userRequest4 = new UserRequest("Birva", "lakhtariya", "d@gmail.com", "774-253-7777");
            createUser(asJsonString(userRequest1));
            createUser(asJsonString(userRequest2));
            createUser(asJsonString(userRequest3));
            createUser(asJsonString(userRequest4));
        }

        private String createUser(String requestAsString) throws Exception {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/users")
                    .content(requestAsString)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            return result.getResponse().getContentAsString();
        }

        private User getUserObject(String userResponse) throws JsonProcessingException {
            return objectMapper.readValue(asJsonString(userResponse), User.class);
        }
    }
}
