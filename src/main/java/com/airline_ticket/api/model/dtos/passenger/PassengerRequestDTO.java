package com.airline_ticket.api.model.dtos.passenger;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PassengerRequestDTO(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String name,

        @NotBlank(message = "Email cannot be blank")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotBlank(message = "Phone cannot be blank")
        @Size(min = 10, max = 15, message = "Phone number must be between 9 and 15 characters")
        String phone
) {
}