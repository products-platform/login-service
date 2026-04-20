package com.web.demo.records;

import com.web.demo.enums.RoleName;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

public record UserRequest(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20)
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank
        @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
        String phone,

        @NotNull
        @Past(message = "DOB must be in the past")
        LocalDate dob,

        Set<RoleName> roles
) {}
