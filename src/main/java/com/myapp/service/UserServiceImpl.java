package com.myapp.service;


import com.myapp.domain.User;
import com.myapp.error.BadRequestException;
import com.myapp.error.RecordNotFoundException;
import com.myapp.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User create(User newUser) {
    return userRepository.save(newUser);
  }

  @Override
  public Optional<User> findById(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent()) {
      throw new RecordNotFoundException("user", id);
    }
    return user;
  }

}
