package com.airline_ticket.api.model.dtos.login_register;

public record ResponseDTO(String name, String token, String expiresAt) {
}
