package com.myapp.api;

import com.myapp.domain.User;
import com.myapp.error.RecordNotFoundException;
import com.myapp.model.UserRequest;
import com.myapp.model.UserResponse;
import com.myapp.service.UserService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
    User newUser = userService.create(getUserFromRequest(request));
    return new ResponseEntity<>(
        new UserResponse(newUser.getId(), newUser.getFirstName(), newUser.getLastName(),
            newUser.getEmail(), newUser.getPhoneNumber()), HttpStatus.OK);

  }

  @GetMapping("/users/{user_id}")
  @ResponseBody
  public ResponseEntity<Optional<User>> findByUserId(@PathVariable("user_id") Long userId) {

    Optional<User> user = userService.findById(userId);
    if (!user.isPresent()) {
      throw new RecordNotFoundException("User", userId);
    }
    return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
  }

  private User getUserFromRequest(UserRequest request) {
    return new User(request.getFirstName(), request.getLastName(), request.getEmail(),
        request.getPhoneNumber());
  }
}
