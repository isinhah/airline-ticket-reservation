package com.airline_ticket.api.controller.swagger;

import com.airline_ticket.api.model.dtos.ticket.TicketDTO;
import com.airline_ticket.api.model.dtos.ticket.TicketRequestDTO;
import com.airline_ticket.api.model.dtos.ticket.TicketUpdateDTO;
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

@Tag(name = "Tickets Controller")
public interface TicketControllerInterface {

    @Operation(summary = "Find all tickets", description = "Retrieve a list of all tickets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all tickets retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<TicketDTO> findAllTickets(
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find ticket by number or reservation", description = "Retrieve a ticket by its ticket number or reservation ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "At least one parameter (ticketNumber or reservationId) must be provided"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    TicketDTO findTicket(
            @Parameter(description = "Ticket number to filter tickets", required = false) String ticketNumber,
            @Parameter(description = "Reservation ID to filter tickets", required = false) UUID reservationId);

    @Operation(summary = "Find ticket by ID", description = "Retrieve a ticket by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    TicketDTO findTicketById(
            @Parameter(description = "ID of the ticket to retrieve", required = true) UUID id);

    @Operation(summary = "Create a new ticket", description = "Create a new ticket record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Reservation or flight not found"),
            @ApiResponse(responseCode = "409", description = "Ticket number already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<TicketDTO> createTicket(
            @Valid @Parameter(description = "Details of the ticket to create", required = true) TicketRequestDTO requestDTO);

    @Operation(summary = "Update an existing ticket", description = "Update an existing ticket record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket updated successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<TicketDTO> updateTicket(
            @Valid @Parameter(description = "Details of the ticket to update", required = true) TicketUpdateDTO updateDTO);

    @Operation(summary = "Delete a ticket", description = "Delete a ticket record by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ticket deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<Void> deleteTicket(
            @Parameter(description = "ID of the ticket to delete", required = true) UUID id);
}