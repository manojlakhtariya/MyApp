package com.myapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.myapp.domain.User;
import com.myapp.error.RecordNotFoundException;
import com.myapp.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  private UserService userService;

  @Mock
  UserRepository userRepository;

  @BeforeEach
  void setup() {
    this.userService = new UserServiceImpl(this.userRepository);
  }


  @Test
  void createUser() {
    User testUser = new User("mark", "smith", "email@email.com", "555-555-5555");
    given(this.userRepository.save(any(User.class))).willReturn(testUser);
    User newUser = userService.create(testUser);

    assertEquals(newUser.getFirstName(), "mark");
    assertEquals(newUser.getLastName(), "smith");
    assertEquals(newUser.getEmail(), "email@email.com");
    assertEquals(newUser.getPhoneNumber(), "555-555-5555");
  }

  @Test
  void findById() {
    Optional<User> testUser = Optional
        .of(new User("mark", "smith", "email@email.com", "555-555-5555"));
    given(this.userRepository.findById(any(Long.class))).willReturn(testUser);

    User newUser = userService.findById(1L).get();

    assertEquals(newUser.getFirstName(), "mark");
    assertEquals(newUser.getLastName(), "smith");
    assertEquals(newUser.getEmail(), "email@email.com");
    assertEquals(newUser.getPhoneNumber(), "555-555-5555");
  }

  @Test
  void findByIdRecordNotFoundException() {
    given(this.userRepository.findById(any(Long.class)))
        .willThrow(new RecordNotFoundException("User", 5L));

    Exception exception = assertThrows(RecordNotFoundException.class, () -> {
      userService.findById(5L);
    });

    assertEquals(exception.getLocalizedMessage(), "Unable to find User with id 5");
  }
}
