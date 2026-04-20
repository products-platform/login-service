package com.web.demo.records;

import com.web.demo.enums.RoleName;

import java.time.LocalDate;
import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        String phone,
        LocalDate dob,
        Set<RoleName> roles
) {}
