package com.myapp.service;

import com.myapp.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface UserService {
    User create(User user);
    List<User> findAllUsers();
    Optional<User> findById(Long id);
}
