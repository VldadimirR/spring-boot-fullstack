package ru.raisbex.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}