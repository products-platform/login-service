package com.web.demo.mappers;

import com.web.demo.models.Role;
import com.web.demo.models.User;
import com.web.demo.records.UserRequest;
import com.web.demo.records.UserResponse;

import java.util.Set;

public interface UserMapper {
    User toEntity(UserRequest request, Set<Role> roles);

    UserResponse toResponse(User save);
}
