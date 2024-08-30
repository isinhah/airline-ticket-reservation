package com.airline_ticket.api.model.dtos.passenger;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Passenger Request DTO", description = "DTO for creating a passenger")
public record PassengerRequestDTO(
        @Schema(description = "Name of the passenger.", example = "Isabel")
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String name,

        @Schema(description = "Email of the passenger.", example = "isabel@example.com")
        @NotBlank(message = "Email cannot be blank")
        String email,

        @Schema(description = "Password of the passenger. Must be at least 8 characters long.", example = "1234567890")
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @Schema(description = "Phone number of the passenger. Must be between 10 and 15 characters long.", example = "+1987654321")
        @NotBlank(message = "Phone cannot be blank")
        @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
        String phone
) {
}