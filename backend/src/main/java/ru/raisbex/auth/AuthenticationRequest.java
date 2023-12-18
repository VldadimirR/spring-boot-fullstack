package ru.raisbex.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}