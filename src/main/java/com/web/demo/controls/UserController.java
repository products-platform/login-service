package com.web.demo.controls;

import com.web.demo.records.UserRequest;
import com.web.demo.records.UserResponse;
import com.web.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
@SecurityRequirement(name = "bearerAuth") // 🔐 JWT required
public class UserController {

    private final UserService service;

    @Operation(
            summary = "Create new user",
            description = "Creates a user with roles. Only ADMIN can access"
    )
    @ApiResponse(responseCode = "200", description = "User created successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "400", description = "Validation error")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return service.createUser(request);
    }

    @Operation(
            summary = "Get all users",
            description = "Accessible by ADMIN and EMPLOYEE"
    )
    @ApiResponse(responseCode = "200", description = "List of users returned")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public List<UserResponse> getAll() {
        return service.getAllUsers();
    }

    /*@PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        user.setPasswordChangedAt(LocalDate.now());

        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully");
    }*/
}
