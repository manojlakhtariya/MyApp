package com.myapp;

import com.myapp.domain.User;
import com.myapp.error.BadRequestException;
import com.myapp.error.RecordNotFoundException;
import com.myapp.model.AppointmentRequest;
import com.myapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AppointmentControllerIngrationTest extends AbstractContainerTest {

    @Autowired
    private MockMvc mvc;

    private User organizer;

    private Set<User> attendees;

    @Autowired
    UserRepository userRepository;


    @Test
    public void createAppointmentTest() throws Exception {
        LocalTime startTime = LocalTime.now().plusHours(1);
        LocalTime endTime = startTime.plusHours(1);
        User user1 = userRepository.findById(1L).get();
        User user2 = userRepository.findById(2L).get();
        User user3 = userRepository.findById(3L).get();
        User user4 = userRepository.findById(4L).get();

        Set<User> attendees = new HashSet<>();
        attendees.add(user2);
        attendees.add(user3);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startTime, endTime, user1, attendees, "Demo meeting");

        String requestString = asJsonString(appointmentRequest);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/appointments")
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").exists());
        //test appointment conflict
        attendees.remove(user2);
        AppointmentRequest conflictRequest = new AppointmentRequest(startTime, endTime, user4, attendees, "conflict meeting");
        String conflictRequestString = asJsonString(conflictRequest);


        mvc.perform(MockMvcRequestBuilders
                .post("/api/appointments")
                .content(conflictRequestString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof BadRequestException))
        .andExpect(mvcResult -> assertEquals("Users have conflict", mvcResult.getResolvedException().getMessage()));

    }


}
