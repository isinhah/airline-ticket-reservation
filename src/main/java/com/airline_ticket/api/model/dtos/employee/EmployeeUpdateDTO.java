package com.airline_ticket.api.model.dtos.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(name = "Employee Update DTO", description = "DTO for updating an employee")
public record EmployeeUpdateDTO(

        @Schema(description = "Unique identifier for the employee.", example = "b2c3e7d5-6789-4abc-1234-56789abcdef0")
        @NotNull(message = "ID cannot be null")
        UUID id,

        @Schema(description = "Full name of the employee.", example = "Isabel Henrique")
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
        String name,

        @Schema(description = "Email of the employee.", example = "isabel@example.com")
        @NotBlank(message = "Email cannot be blank")
        String email,

        @Schema(description = "Password of the employee.", example = "1234567890")
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {
}