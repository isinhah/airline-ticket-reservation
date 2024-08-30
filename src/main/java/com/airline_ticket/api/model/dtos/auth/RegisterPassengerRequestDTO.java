package com.airline_ticket.api.model.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Register Passenger RequestDTO", description = "DTO for registering a new passenger")
public record RegisterPassengerRequestDTO(
        @Schema(description = "Name of the passenger.", example = "Isabel")
        @NotBlank(message = "Name is mandatory")
        String name,

        @Schema(description = "Email of the passenger.", example = "isabel@example.com")
        @NotBlank(message = "Email is mandatory")
        String email,

        @Schema(description = "Password for the passenger account.", example = "1234567890")
        @NotBlank(message = "Password is mandatory")
        String password,

        @Schema(description = "Phone number of the passenger.", example = "+1234567890")
        @NotBlank(message = "Phone is mandatory")
        String phone
) {
}