package com.airline_ticket.api.model.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Register Employee Request DTO", description = "DTO for registering a new employee")
public record RegisterEmployeeRequestDTO(
        @Schema(description = "Name of the employee.", example = "Isabel Henrique")
        @NotBlank(message = "Name is mandatory")
        String name,

        @Schema(description = "Email of the employee.", example = "isabel@example.com")
        @NotBlank(message = "Email is mandatory")
        String email,

        @Schema(description = "Password for the employee account.", example = "1234567890")
        @NotBlank(message = "Password is mandatory")
        String password
) {
}