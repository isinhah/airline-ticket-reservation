package com.airline_ticket.api.model.dtos.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "Ticket Request DTO", description = "DTO for creating a new ticket")
public record TicketRequestDTO(

        @Schema(description = "Unique ticket number.", example = "TICK123456")
        @NotBlank(message = "Ticket number cannot be blank")
        String ticketNumber,

        @Schema(description = "Unique identifier for the reservation associated with this ticket.", example = "b2c1a3d4-567e-890f-ab12-cd34ef567890")
        @NotNull(message = "Reservation ID cannot be null")
        UUID reservationId,

        @Schema(description = "Unique identifier for the flight associated with this ticket.", example = "e7d7e6f5-1c4d-41b4-851e-c8d9e12c5b5e")
        @NotNull(message = "Flight ID cannot be null")
        UUID flightId
) {}