package com.myapp.service;

import com.myapp.domain.User;
import com.myapp.repository.AppointmentRepository;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {

  @Mock
  AppointmentRepository appointmentRepository;

  private AppointmentService appointmentService;

  @BeforeEach
  void setUp() {
    this.appointmentService = new AppointmentServiceImpl(this.appointmentRepository);
  }

  @Test
  void createAppointmentBadRequestException() {

  }
}
