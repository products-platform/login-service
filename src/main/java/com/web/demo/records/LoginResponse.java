package com.web.demo.records;

import java.util.Set;

public record LoginResponse(String token,
                            String username,
                            Set<String> roles) {
}
