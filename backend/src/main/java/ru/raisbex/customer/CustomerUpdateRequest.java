package ru.raisbex.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
