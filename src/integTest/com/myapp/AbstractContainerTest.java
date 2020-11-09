package com.myapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myapp.AbstractContainerTest.ContainerResourceConfiguration;
import com.myapp.domain.Appointment;
import com.myapp.domain.User;
import com.myapp.model.AppointmentRequest;
import com.myapp.service.AppointmentService;
import com.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;

@ActiveProfiles("integration_test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = ContainerResourceConfiguration.class)
@AutoConfigureMockMvc
public abstract class AbstractContainerTest {

  private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7")
      .withDatabaseName("spring_app_db")
      .withExposedPorts(3306).withEnv("TZ", "US/Eastern");
  public static ObjectMapper objectMapper;

  static {
    mysql.start();
    System.setProperty("spring.datasource.username", mysql.getUsername());
    System.setProperty("spring.datasource.password", mysql.getPassword());
    System.setProperty("spring.datasource.url", mysql.getJdbcUrl());
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Autowired
  UserService userService;
  @Autowired
  AppointmentService appointmentService;

  public static String asJsonString(final Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static AppointmentRequest getAppointmentRequestFromString(String request)
      throws JsonProcessingException {
    return objectMapper.readValue(request, AppointmentRequest.class);
  }

  public User createUser(User user) {
    return userService.create(user);
  }

  public Appointment creatAppointment(Appointment appointment) {
    return appointmentService.createAppointment(appointment);
  }

  @TestConfiguration
  static class ContainerResourceConfiguration {

  }
}
