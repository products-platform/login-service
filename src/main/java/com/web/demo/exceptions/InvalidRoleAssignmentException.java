package com.web.demo.exceptions;

public class InvalidRoleAssignmentException extends RuntimeException {
    public InvalidRoleAssignmentException(String message) {
        super(message);
    }
}
