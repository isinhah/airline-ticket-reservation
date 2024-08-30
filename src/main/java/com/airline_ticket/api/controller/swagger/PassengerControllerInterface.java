package com.airline_ticket.api.controller.swagger;

import com.airline_ticket.api.model.dtos.passenger.PassengerDTO;
import com.airline_ticket.api.model.dtos.passenger.PassengerRequestDTO;
import com.airline_ticket.api.model.dtos.passenger.PassengerUpdateDTO;
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

@Tag(name = "Passengers Controller")
public interface PassengerControllerInterface {

    @Operation(summary = "Find all passengers", description = "Retrieve a list of all passengers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all passengers retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<PassengerDTO> findAllPassengers(
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find passenger by ID", description = "Retrieve a passenger by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    PassengerDTO findPassengerById(
            @Parameter(description = "ID of the passenger to retrieve", required = true) UUID id);

    @Operation(summary = "Find passengers by name", description = "Retrieve a list of passengers by their name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of passengers by name retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<PassengerDTO> findPassengersByName(
            @Parameter(description = "Name to search for", required = true) String name,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find passenger by phone or email", description = "Retrieve a passenger by their phone number or email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "400", description = "At least one parameter (phone or email) must be provided"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    PassengerDTO findPassengerByPhoneOrEmail(
            @Parameter(description = "Phone number of the passenger to retrieve", required = false) String phone,
            @Parameter(description = "Email address of the passenger to retrieve", required = false) String email);

    @Operation(summary = "Create a new passenger", description = "Create a new passenger record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Passenger created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<PassengerDTO> createPassenger(
            @Valid @Parameter(description = "Details of the passenger to create", required = true) PassengerRequestDTO passengerRequestDTO);

    @Operation(summary = "Update an existing passenger", description = "Update an existing passenger record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger updated successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<PassengerDTO> updatePassenger(
            @Valid @Parameter(description = "Details of the passenger to update", required = true) PassengerUpdateDTO passengerUpdateDTO);

    @Operation(summary = "Delete a passenger", description = "Delete a passenger record by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Passenger deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<Void> deletePassenger(
            @Parameter(description = "ID of the passenger to delete", required = true) UUID id);
}