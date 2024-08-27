package com.airline_ticket.api.model.dtos.login_register;

import jakarta.validation.constraints.NotBlank;

public record RegisterEmployeeRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Email is mandatory")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password) {
}