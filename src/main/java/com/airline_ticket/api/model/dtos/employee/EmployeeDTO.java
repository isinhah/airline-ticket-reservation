package com.airline_ticket.api.model.dtos.employee;

import com.airline_ticket.api.model.Employee;

import java.util.UUID;

public record EmployeeDTO(
        UUID id,
        String name,
        String email,
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
