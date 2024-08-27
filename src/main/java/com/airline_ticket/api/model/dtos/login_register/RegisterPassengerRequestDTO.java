package com.airline_ticket.api.model.dtos.login_register;

import jakarta.validation.constraints.NotBlank;

public record RegisterPassengerRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Email is mandatory")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password,

        @NotBlank(message = "Phone is mandatory")
        String phone
) {
}