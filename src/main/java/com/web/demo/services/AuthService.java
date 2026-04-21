package com.web.demo.services;

import com.web.demo.records.LoginRequest;
import com.web.demo.records.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest req);
}
