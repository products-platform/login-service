package com.web.demo.services;

import com.web.demo.enums.RoleName;
import com.web.demo.exceptions.DatabaseExceptionMapper;
import com.web.demo.exceptions.InvalidRoleAssignmentException;
import com.web.demo.exceptions.MissingDefaultRoleException;
import com.web.demo.mappers.UserMapper;
import com.web.demo.models.Role;
import com.web.demo.models.User;
import com.web.demo.records.UserRequest;
import com.web.demo.records.UserResponse;
import com.web.demo.repos.RoleRepository;
import com.web.demo.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final DatabaseExceptionMapper exceptionMapper;

    @Override
    public UserResponse createUser(UserRequest request) {
        Set<Role> roles = resolveRoles(request.roles());

        User user = mapper.toEntity(request, roles);
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            return mapper.toResponse(userRepository.save(user));
        } catch (DataIntegrityViolationException ex) {
            throw exceptionMapper.mapDuplicate(ex);
        }
    }

    private Set<Role> resolveRoles(Set<RoleName> requestedRoles) {

        // Always assign USER by default
        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(MissingDefaultRoleException::new);

        // If no roles provided → just USER
        if (requestedRoles == null || requestedRoles.isEmpty()) {
            return Set.of(userRole);
        }

        // ❌ Reject invalid role requests instead of silently ignoring
        for (RoleName roleName : requestedRoles) {
            if (roleName != RoleName.USER) {
                throw new InvalidRoleAssignmentException(
                        "Only USER role is allowed during signup"
                );
            }
        }

        return Set.of(userRole);
    }

    private Role getDefaultUserRole() {
        return roleRepository.findByName(RoleName.USER)
                .orElseThrow(() ->
                        new IllegalStateException("Default role USER not found"));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
