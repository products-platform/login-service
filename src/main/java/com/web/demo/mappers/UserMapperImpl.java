package com.web.demo.mappers;

import com.web.demo.enums.RoleName;
import com.web.demo.models.Role;
import com.web.demo.models.User;
import com.web.demo.records.UserRequest;
import com.web.demo.records.UserResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

    public User toEntity(UserRequest req, Set<Role> roles) {
        return User.builder()
                .username(req.username())
                .email(req.email())
                .phone(req.phone())
                .dob(req.dob())
                .password(req.password())
                .enabled(true)
                .roles(roles)
                .build();

    }

    public UserResponse toResponse(User user) {
        Set<RoleName> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getDob(),
                roles
        );
    }
}
