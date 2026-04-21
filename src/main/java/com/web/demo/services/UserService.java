package com.web.demo.services;

import com.web.demo.models.User;
import com.web.demo.records.UserRequest;
import com.web.demo.records.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse createUser(UserRequest request);
    List<UserResponse> getAllUsers();

    Optional<User> findByUsername(String username);

    void save(User user);
}
