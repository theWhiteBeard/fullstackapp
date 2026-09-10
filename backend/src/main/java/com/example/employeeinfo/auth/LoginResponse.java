package com.example.employeeinfo.auth;

public record LoginResponse(
        String token,
        String username,
        String fullName
) {
}
