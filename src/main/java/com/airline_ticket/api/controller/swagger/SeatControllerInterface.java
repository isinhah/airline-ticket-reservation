package com.airline_ticket.api.controller.swagger;

import com.airline_ticket.api.model.dtos.seat.SeatDTO;
import com.airline_ticket.api.model.dtos.seat.SeatRequestDTO;
import com.airline_ticket.api.model.dtos.seat.SeatUpdateDTO;
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

@Tag(name = "Seats Controller")
public interface SeatControllerInterface {

    @Operation(summary = "Find all seats", description = "Retrieve a list of all seats.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all seats retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<SeatDTO> findAllSeats(
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find seat by ID", description = "Retrieve a seat by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    SeatDTO findSeatById(
            @Parameter(description = "ID of the seat to retrieve", required = true) UUID id);

    @Operation(summary = "Find available seats by flight", description = "Retrieve a list of available seats for a specific flight.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available seats retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<SeatDTO> findAvailableSeats(
            @Parameter(description = "ID of the flight to retrieve available seats for", required = true) UUID flightId,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find seats by number or flight", description = "Retrieve a list of seats filtered by seat number or flight ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of seats retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "At least one parameter (seatNumber or flightId) must be provided"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<SeatDTO> findSeats(
            @Parameter(description = "Seat number to filter seats", required = false) String seatNumber,
            @Parameter(description = "Flight ID to filter seats", required = false) UUID flightId,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Create a new seat", description = "Create a new seat record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Seat created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<SeatDTO> createSeat(
            @Valid @Parameter(description = "Details of the seat to create", required = true) SeatRequestDTO requestDTO);

    @Operation(summary = "Update an existing seat", description = "Update an existing seat record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat updated successfully"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<SeatDTO> updateSeat(
            @Valid @Parameter(description = "Details of the seat to update", required = true) SeatUpdateDTO updateDTO);

    @Operation(summary = "Delete a seat", description = "Delete a seat record by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Seat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<Void> deleteSeat(
            @Parameter(description = "ID of the seat to delete", required = true) UUID id);
}