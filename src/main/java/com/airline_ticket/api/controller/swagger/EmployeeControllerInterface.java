package com.airline_ticket.api.controller.swagger;

import com.airline_ticket.api.model.dtos.employee.EmployeeDTO;
import com.airline_ticket.api.model.dtos.employee.EmployeeRequestDTO;
import com.airline_ticket.api.model.dtos.employee.EmployeeUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "Employees Controller")
public interface EmployeeControllerInterface {

    @Operation(summary = "Find all employees", description = "Retrieve a list of all employees with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all employees retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<EmployeeDTO> findAllEmployees(
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find employee by ID", description = "Retrieve a specific employee by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    EmployeeDTO findEmployeeById(
            @Parameter(description = "ID of the employee to retrieve", required = true) UUID id);

    @Operation(summary = "Find employee by email", description = "Retrieve a specific employee by their email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    EmployeeDTO findEmployeeByEmail(
            @Parameter(description = "Email of the employee to retrieve", required = true) String email);

    @Operation(summary = "Find employees by name", description = "Retrieve a list of employees by their name with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees by name retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<EmployeeDTO> findEmployeesByName(
            @Parameter(description = "Name of the employees to retrieve", required = true) String name,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Create a new employee", description = "Create a new employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<EmployeeDTO> createEmployee(
            @Valid @Parameter(description = "Details of the employee to create", required = true) EmployeeRequestDTO requestDTO);

    @Operation(summary = "Update an existing employee", description = "Update an existing employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<EmployeeDTO> updateEmployee(
            @Valid @Parameter(description = "Details of the employee to update", required = true) EmployeeUpdateDTO updateDTO);

    @Operation(summary = "Delete an employee", description = "Delete an employee by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "ID of the employee to delete", required = true) UUID id);
}