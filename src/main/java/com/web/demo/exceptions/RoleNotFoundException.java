package com.web.demo.exceptions;

public class RoleNotFoundException extends RuntimeException {

    private final String role;

    public RoleNotFoundException(String role) {
        super("Role not found: " + role);
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
