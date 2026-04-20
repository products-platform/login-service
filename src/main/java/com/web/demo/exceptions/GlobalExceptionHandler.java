package com.web.demo.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problem.setTitle("Internal Server Error");
        problem.setDetail(getRootCause(ex).getMessage());
        problem.setProperty("path", request.getRequestURI());
        problem.setProperty("timestamp", LocalDateTime.now());

        return problem;
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ProblemDetail handleDuplicateUser(DuplicateUserException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        problem.setTitle("Duplicate Resource");
        problem.setDetail(ex.getMessage());
        problem.setProperty("field", ex.getField());
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Validation Failed");
        problem.setDetail("One or more fields are invalid");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );

        problem.setProperty("errors", errors);
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ProblemDetail handleRoleNotFound(RoleNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Invalid Role");
        problem.setDetail(ex.getMessage());
        problem.setProperty("role", ex.getRole());

        return problem;
    }

    @ExceptionHandler(MissingDefaultRoleException.class)
    public ProblemDetail handleMissingDefaultRole(MissingDefaultRoleException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problem.setTitle("Server Misconfiguration");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    @ExceptionHandler(InvalidRoleAssignmentException.class)
    public ProblemDetail handleInvalidRole(InvalidRoleAssignmentException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);

        problem.setTitle("Invalid Role Assignment");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    // 🔍 Extract real root cause
    private Throwable getRootCause(Throwable ex) {
        Throwable root = ex;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        return root;
    }
}
