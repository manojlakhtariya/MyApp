package com.myapp.service;

import com.myapp.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

  User create(User user);

  Optional<User> findById(Long id);
}
