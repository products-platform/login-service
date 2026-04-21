package com.web.demo.controls;

import com.web.demo.models.User;
import com.web.demo.records.LoginRequest;
import com.web.demo.records.LoginResponse;
import com.web.demo.services.AuthService;
import com.web.demo.services.UserService;
import com.web.demo.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Auth APIs for login and token generation")
public class AuthRestController {

    private final AuthenticationManager manager;
    private final JwtTokenUtil jwtUtil;
    private final UserService userService;
    private final AuthService authService;

    @Operation(
            summary = "Login user",
            description = "Authenticate user and return JWT token"
    )
    @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }
   /* public LoginResponse login(@RequestBody LoginRequest req) {
        User user = userService.findByUsername(req.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Check if account locked
        if (!user.isAccountNonLocked()) {

            // Optional auto-unlock after 15 min
            if (user.getLockTime() != null &&
                    user.getLockTime().plusMinutes(15).isBefore(LocalDateTime.now())) {

                user.unlockAccount();
                userService.save(user);

            } else {
                throw new LockedException("Account locked. Try again later.");
            }
        }

        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.username(),
                            req.password()
                    )
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // ✅ SUCCESS → reset attempts
            user.resetFailedAttempts();
            userService.save(user);

            Set<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            String token = jwtUtil.generateToken(
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getPhone(),
                    roles
            );

            return new LoginResponse(token, userDetails.getUsername(), roles);

        } catch (BadCredentialsException ex) {

            // ❌ FAILED → increase attempts
            user.increaseFailedAttempts();

            if (user.getFailedAttempts() >= 3) {
                user.lockAccount();
            }

            userService.save(user);

            throw new BadCredentialsException("Invalid username or password");
        }
    }*/

    /*@PostMapping("/login")
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
    }*/

    @PostMapping("/unlock")
    public ResponseEntity<?> unlock(@RequestParam String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.unlockAccount();
        userService.save(user);
        return ResponseEntity.ok("Account unlocked");
    }
}
