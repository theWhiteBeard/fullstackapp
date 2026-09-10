package com.example.employeeinfo.auth;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthServiceTest {

    private final AuthService authService = new AuthService();

    @Test
    void authenticatesKnownUser() {
        Optional<LoginResponse> result =
                authService.authenticate(new LoginRequest("admin", "admin123"));

        assertTrue(result.isPresent());
        assertEquals("admin", result.get().username());
        assertEquals("Site Administrator", result.get().fullName());
        assertFalse(result.get().token().isBlank());
    }

    @Test
    void rejectsWrongPassword() {
        assertTrue(authService.authenticate(
                new LoginRequest("admin", "nope")).isEmpty());
    }

    @Test
    void rejectsUnknownUser() {
        assertTrue(authService.authenticate(
                new LoginRequest("ghost", "whatever")).isEmpty());
    }
}
