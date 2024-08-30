package com.airline_ticket.api.model.dtos.employee;

import com.airline_ticket.api.model.Employee;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "Employee DTO", description = "DTO for Employee")
public record EmployeeDTO(
        @Schema(description = "Unique identifier for the employee.", example = "b2c3e7d5-6789-4abc-1234-56789abcdef0")
        UUID id,

        @Schema(description = "Full name of the employee.", example = "Isabel Henrique")
        String name,

        @Schema(description = "Email of the employee.", example = "isabel@example.com")
        String email,

        @Schema(description = "Password of the employee.", example = "1234567890")
        String password
) {
    public static EmployeeDTO employeeToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPassword()
        );
    }
}