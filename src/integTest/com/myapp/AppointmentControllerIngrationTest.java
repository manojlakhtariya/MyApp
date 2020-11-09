package com.myapp;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.myapp.domain.Appointment;
import com.myapp.domain.TimeSlot;
import com.myapp.domain.User;
import com.myapp.error.BadRequestException;
import com.myapp.model.AppointmentRecommandationRequest;
import com.myapp.model.AppointmentRequest;
import com.myapp.model.UpdateAppointmentUsers;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class AppointmentControllerIngrationTest extends AbstractContainerTest {

  @Autowired
  private MockMvc mvc;


  @Test
  public void createAppointmentTest() throws Exception {
    LocalTime startTime = LocalTime.now().plusHours(1);
    LocalTime endTime = startTime.plusHours(1);
    TimeSlot timeSlot = new TimeSlot(startTime, endTime);

    //create users
    User user1 = createUser(new User("Manoj", "lakhtariya", "b@gmail.com", "222-222-2222"));
    User user2 = createUser(new User("kin", "kee", "e@gmail.com", "555-555-5555"));
    User user3 = createUser(new User("Shayanth", "Sinnarajah", "f@gmail.com", "666-666-6666"));
    User user4 = createUser(new User("Praveendra", "Singh", "g@gmail.com", "777-777-7777"));

    Set<User> attendees = new HashSet<>();
    attendees.add(user2);
    attendees.add(user3);

    AppointmentRequest appointmentRequest = new AppointmentRequest(timeSlot, user1, attendees,
        "Demo meeting");
    String requestString = asJsonString(appointmentRequest);

    mvc.perform(MockMvcRequestBuilders
        .post("/api/appointments")
        .content(requestString)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(".id").exists());
  }

  @Test
  public void appointmentConflictTest() throws Exception {
    User user1 = createUser(new User("bob", "phillip", "a@gmail.com", "999-999-999"));
    User user2 = createUser(new User("kevin", "jackson", "d@gmail.com", "777-666-6666"));
    User user3 = createUser(new User("dan", "boland", "ee@gamil.com", "333-333-3333"));
    User user4 = createUser(new User("mark", "bond", "dd@gamil.com", "444-333-4444"));

    Set<User> attendees = new HashSet<>();
    attendees.add(user2);
    attendees.add(user3);

    Appointment appointment = creatAppointment(new Appointment(LocalDate.now(),
        new TimeSlot(LocalTime.now(), LocalTime.now().plusHours(1)), user1, attendees,
        Appointment.AppointmentStatus.SCHEDULED, "First meeting"));

    attendees.remove(user2);
    AppointmentRequest conflictRequest = new AppointmentRequest(
        new TimeSlot(LocalTime.now(), LocalTime.now().plusHours(2)), user4, attendees,
        "conflict meeting");

    String conflictRequestString = asJsonString(conflictRequest);

    mvc.perform(MockMvcRequestBuilders
        .post("/api/appointments")
        .content(conflictRequestString)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(mvcResult -> assertTrue(
            mvcResult.getResolvedException() instanceof BadRequestException))
        .andExpect(mvcResult -> assertEquals("Users have conflict",
            mvcResult.getResolvedException().getMessage()));
  }

  @Test
  public void findAppointmentByIdTest() throws Exception {

    User user1 = createUser(new User("jig", "patel", "box@gmail.com", "999-999-8888"));
    User user2 = createUser(new User("adam", "ludwig", "adam@gmail.com", "999-343-2343"));

    Set<User> attendees = new HashSet<>();
    attendees.add(user2);

    Appointment appointment = creatAppointment(new Appointment(LocalDate.now(),
        new TimeSlot(LocalTime.now(), LocalTime.now().plusHours(1)), user1, attendees,
        Appointment.AppointmentStatus.SCHEDULED, "get meeting test"));

    mvc.perform(MockMvcRequestBuilders
        .get("/api/appointments/{appointmentId}", appointment.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(".id").exists());
  }


  @Test
  public void getRecommandationsTest() throws Exception {
    User user1 = createUser(new User("james", "smith", "james@email.com", "111-777-7777"));
    User user2 = createUser(new User("Michael", "smith", "msmith@email.com", "111-666-7777"));
    User user3 = createUser(new User("Robert", "smith", "rob@email.com", "111-777-8888"));
    User user4 = createUser(new User("David", "smith", "david@email.com", "111-777-9999"));
    User user5 = createUser(new User("Maria", "smith", "maria@email.com", "111-456-9999"));
    User user6 = createUser(new User("marry", "smith", "marry@email.com", "111-456-2222"));

    Set<User> meeting1Attendees = new HashSet<>();
    meeting1Attendees.add(user2);
    meeting1Attendees.add(user3);

    LocalTime midnight = LocalTime.MIDNIGHT;
    //meeting for user1, user2 and user3 at 7 to 9
    Appointment appointment1 = creatAppointment(
        new Appointment(LocalDate.now(), new TimeSlot(midnight.plusHours(7), midnight.plusHours(9)),
            user1, meeting1Attendees, Appointment.AppointmentStatus.SCHEDULED, "First meeting"));

    Set<User> meeting2Attendees = new HashSet<>();
    meeting2Attendees.add(user4);
    meeting2Attendees.add(user5);
    //meeting for user4, user5 and user6 at 8 to 10
    Appointment appointment2 = creatAppointment(new Appointment(LocalDate.now(),
        new TimeSlot(midnight.plusHours(8), midnight.plusHours(10)), user6, meeting2Attendees,
        Appointment.AppointmentStatus.SCHEDULED, "First meeting"));

    Set<User> meeting3Attendees = new HashSet<>();
    meeting3Attendees.add(user2);
    //meeting for user4, user2 at 10 to 11
    Appointment appointment3 = creatAppointment(new Appointment(LocalDate.now(),
        new TimeSlot(midnight.plusHours(10), midnight.plusHours(11)), user4, meeting3Attendees,
        Appointment.AppointmentStatus.SCHEDULED, "second meeting"));

    Set<User> attendees = new HashSet<>();
    attendees.add(user2);
    attendees.add(user3);
    attendees.add(user4);
    attendees.add(user5);

    //user 5 trying to setup meeting with user2, user3, user4, user5
    AppointmentRecommandationRequest request = new AppointmentRecommandationRequest(LocalDate.now(),
        attendees);

    String requestString = asJsonString(request);

    mvc.perform(MockMvcRequestBuilders
        .get("/api/appointments/availability")
        .content(requestString)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.timeslots", hasSize(3)));
  }

  @Test
  public void updateUsersTest() throws Exception {
    User user1 = createUser(new User("mark", "smith", "james@email.com", "111-777-7777"));
    User user2 = createUser(new User("bobby", "smith", "msmith@email.com", "111-666-7777"));
    User user3 = createUser(new User("ann", "smith", "rob@email.com", "111-777-8888"));
    User user4 = createUser(new User("dilip", "patel", "david@email.com", "111-777-9999"));

    Set<User> meeting1Attendees = new HashSet<>();
    meeting1Attendees.add(user2);
    meeting1Attendees.add(user3);

    LocalTime midnight = LocalTime.MIDNIGHT;
    //meeting for user1, user2 and user3 at 7 to 9
    Appointment appointment1 = creatAppointment(
        new Appointment(LocalDate.now(), new TimeSlot(midnight.plusHours(7), midnight.plusHours(9)),
            user1, meeting1Attendees, Appointment.AppointmentStatus.SCHEDULED, "First meeting"));

    Set<User> newUsers = new HashSet<>();
    newUsers.add(user4);
    //add uer4 to appointment
    UpdateAppointmentUsers updateUsers = new UpdateAppointmentUsers(newUsers);

    String requestString = asJsonString(updateUsers);

    mvc.perform(MockMvcRequestBuilders
        .put("/api/appointments/{appointment_id}", appointment1.getId())
        .content(requestString)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.attendees", hasSize(3)));
  }
}
