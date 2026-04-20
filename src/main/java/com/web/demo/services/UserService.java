package com.web.demo.services;

import com.web.demo.records.UserRequest;
import com.web.demo.records.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    List<UserResponse> getAllUsers();
}
