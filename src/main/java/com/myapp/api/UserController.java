package com.myapp.api;

import com.myapp.domain.User;
import com.myapp.error.RecordNotFoundException;
import com.myapp.model.UserRequest;
import com.myapp.model.UserResponse;
import com.myapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request)
    {
        User newUser = userService.create(getUserFromRequest(request));
        return new ResponseEntity<>(new UserResponse(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPhoneNumber()) ,HttpStatus.OK);

    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{user_id}")
    @ResponseBody
    public ResponseEntity<Optional<User>> findByUserId(@PathVariable("user_id") Long userId)
    {

        Optional<User> user = userService.findById(userId);
        if(!user.isPresent()) {
            throw new RecordNotFoundException("User", userId);
        }
        return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
    }

//    @PutMapping("/users/{user_id}")
//    public User update(@PathVariable("user_id") Long userId, @RequestBody User userObject)
//    {
//        return userService.update(userId, userObject);
//    }
//
//
//
//    @DeleteMapping("/users/{user_id}")
//    public List<User> delete(@PathVariable("user_id") Long userId)
//    {
//        return userService.delete(userId);
//    }


    private User getUserFromRequest(UserRequest request) {
        return new User(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPhoneNumber());
    }
}
