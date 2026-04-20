package com.web.demo.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class DatabaseExceptionMapper {

    public DuplicateUserException mapDuplicate(DataIntegrityViolationException ex) {
        Throwable root = getRootCause(ex);
        String message = root.getMessage();

        if (message == null) {
            return new DuplicateUserException("user", "Duplicate user data");
        }

        message = message.toLowerCase();

        if (message.contains("uk_user_username")) {
            return new DuplicateUserException("username", "Username already exists");
        }

        if (message.contains("uk_user_email")) {
            return new DuplicateUserException("email", "Email already exists");
        }

        if (message.contains("uk_user_phone")) {
            return new DuplicateUserException("phone", "Phone already exists");
        }

        return new DuplicateUserException("user", "User already exists");
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        return cause;
    }
}
