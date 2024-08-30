package com.airline_ticket.api.controller.swagger;

import com.airline_ticket.api.model.dtos.auth.LoginRequestDTO;
import com.airline_ticket.api.model.dtos.auth.RegisterEmployeeRequestDTO;
import com.airline_ticket.api.model.dtos.auth.RegisterPassengerRequestDTO;
import com.airline_ticket.api.model.dtos.auth.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication Controller")
public interface AuthControllerInterface {

    @Operation(summary = "Passenger Login", description = "Authenticate a passenger and generate a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<ResponseDTO> loginPassenger(
            @Valid @Parameter(description = "Login credentials for passenger", required = true) LoginRequestDTO dto);

    @Operation(summary = "Passenger Registration", description = "Register a new passenger.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<ResponseDTO> registerPassenger(
            @Valid @Parameter(description = "Registration details for passenger", required = true) RegisterPassengerRequestDTO dto);

    @Operation(summary = "Employee Login", description = "Authenticate an employee and generate a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<ResponseDTO> loginEmployee(
            @Valid @Parameter(description = "Login credentials for employee", required = true) LoginRequestDTO dto);

    @Operation(summary = "Employee Registration", description = "Register a new employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<ResponseDTO> registerEmployee(
            @Valid @Parameter(description = "Registration details for employee", required = true) RegisterEmployeeRequestDTO dto);
}