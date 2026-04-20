package com.web.demo.exceptions;

public class MissingDefaultRoleException extends RuntimeException {

    public MissingDefaultRoleException() {
        super("Default role ROLE_USER is not configured in the system");
    }
}
