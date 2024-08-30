package com.airline_ticket.api.controller.swagger;

import com.airline_ticket.api.model.dtos.reservation.ReservationDTO;
import com.airline_ticket.api.model.dtos.reservation.ReservationRequestDTO;
import com.airline_ticket.api.model.dtos.reservation.ReservationUpdateDTO;
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

@Tag(name = "Reservations Controller")
public interface ReservationControllerInterface {

    @Operation(summary = "Find all reservations", description = "Retrieve a list of all reservations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all reservations retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<ReservationDTO> findAllReservations(
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find reservation by ID", description = "Retrieve a reservation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ReservationDTO findReservationById(
            @Parameter(description = "ID of the reservation to retrieve", required = true) UUID id);

    @Operation(summary = "Find reservations by date", description = "Retrieve a list of reservations by their reservation date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reservations by date retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date format. Please use ISO 8601 format."),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<ReservationDTO> findReservationsByDate(
            @Parameter(description = "Reservation date in ISO 8601 format", required = true) String reservationDate,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find reservations by seat or passenger", description = "Retrieve a list of reservations by seat ID or passenger ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reservations retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "At least one parameter (seatId or passengerId) must be provided"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<ReservationDTO> findReservations(
            @Parameter(description = "Seat ID to filter reservations", required = false) UUID seatId,
            @Parameter(description = "Passenger ID to filter reservations", required = false) UUID passengerId,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Create a new reservation", description = "Create a new reservation record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Seat or passenger not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<ReservationDTO> createReservation(
            @Valid @Parameter(description = "Details of the reservation to create", required = true) ReservationRequestDTO reservationRequestDTO);

    @Operation(summary = "Update an existing reservation", description = "Update an existing reservation record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<ReservationDTO> updateReservation(
            @Valid @Parameter(description = "Details of the reservation to update", required = true) ReservationUpdateDTO reservationUpdateDTO);

    @Operation(summary = "Delete a reservation", description = "Delete a reservation record by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reservation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<Void> deleteReservation(
            @Parameter(description = "ID of the reservation to delete", required = true) UUID id);
}