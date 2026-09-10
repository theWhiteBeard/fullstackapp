package com.example.employeeinfo.auth;

import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Very small in-memory authentication.
 *
 * This is a starting point only - credentials are hard-coded and the "token"
 * is just an opaque random string. Replace with a real user store + JWT later.
 */
@Service
public class AuthService {

    private record Account(String password, String fullName) {
    }

    private static final Map<String, Account> USERS = Map.of(
            "admin", new Account("admin123", "Site Administrator"),
            "jdoe", new Account("password", "Jane Doe")
    );

    public Optional<LoginResponse> authenticate(LoginRequest request) {
        Account account = USERS.get(request.username());
        if (account == null || !account.password().equals(request.password())) {
            return Optional.empty();
        }
        String token = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(UUID.randomUUID().toString().getBytes());
        return Optional.of(new LoginResponse(token, request.username(), account.fullName()));
    }
}
