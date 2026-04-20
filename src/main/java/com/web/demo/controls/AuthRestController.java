package com.web.demo.controls;

import com.web.demo.dtos.CustomUserDetails;
import com.web.demo.records.LoginRequest;
import com.web.demo.records.LoginResponse;
import com.web.demo.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Auth APIs for login and token generation")
public class AuthRestController {

    private final AuthenticationManager manager;
    private final JwtTokenUtil jwtUtil;

    @Operation(
            summary = "Login user",
            description = "Authenticate user and return JWT token"
    )
    @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.username(),
                        req.password()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String username = userDetails.getUsername();
        String email = userDetails.getEmail();
        String phone = userDetails.getPhone();

        String token = jwtUtil.generateToken(username,email,phone,roles);

        return new LoginResponse(
                token,
                userDetails.getUsername(),
                roles
        );
    }
}
