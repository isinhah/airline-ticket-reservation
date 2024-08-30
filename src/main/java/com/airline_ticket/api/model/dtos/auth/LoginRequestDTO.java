package com.airline_ticket.api.model.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Login Request DTO", description = "DTO for Login Request")
public record LoginRequestDTO(
        @Schema(description = "The email address of the user.", example = "isabel@example.com")
        @NotBlank(message = "Email is mandatory")
        String email,

        @Schema(description = "The password for the user account.", example = "1234567890")
        @NotBlank(message = "Password is mandatory")
        String password
) {}
