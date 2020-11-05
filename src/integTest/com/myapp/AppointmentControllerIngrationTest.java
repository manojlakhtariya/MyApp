package com.myapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.domain.User;
import com.myapp.model.AppointmentRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppointmentControllerIngrationTest extends AbstractContainerTest{

    @Autowired
    private MockMvc mvc;

    @Test
    public void createAppointmentTest() throws Exception {
        LocalTime startTime = LocalTime.now().plusHours(1);
        LocalTime endTime = startTime.plusHours(1);
        User organizer = new User("manoj","lakhtairya","e@gmail.com","000-00-0000");
        Set<User> attendees = new HashSet<>();

        User attendee1 = new User("john","wick","b@test.com","888-888-8888");
        User attendee2 = new User("bob","phillip","r@test.com","555-555-5555");

        attendees.add(attendee1);
        attendees.add(attendee2);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startTime, endTime, organizer, attendees, "Demo meeting");

        String requestString = asJsonString(appointmentRequest);

        mvc.perform(MockMvcRequestBuilders
        .post("/api/appointments")
        .content(requestString)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(".id").exists());

        mvc.perform(MockMvcRequestBuilders
                .post("/api/appointments")
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").exists());

    }


}
