package com.web.demo.services;

import com.web.demo.customer.CustomUserDetails;
import com.web.demo.models.User;
import com.web.demo.records.LoginRequest;
import com.web.demo.records.LoginResponse;
import com.web.demo.repos.UserRepository;
import com.web.demo.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager manager;
    private final JwtTokenUtil jwtUtil;

    public LoginResponse login(LoginRequest req) {
        User user = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔒 Check if locked
        if (!user.isAccountNonLocked()) {

            if (user.isLockExpired()) {
                user.unlockAccount(); // auto persisted
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

            // ✅ SUCCESS
            user.resetFailedAttempts(); // auto persisted

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
            // ❌ FAILED
            user.increaseFailedAttempts();

            if (user.getFailedAttempts() >= 3) {
                user.lockAccount();
            }

            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
