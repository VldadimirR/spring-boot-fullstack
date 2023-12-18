package ru.raisbex.auth;

import ru.raisbex.customer.CustomerDTO;

public record AuthenticationResponse (
        String token,
        CustomerDTO customerDTO){
}