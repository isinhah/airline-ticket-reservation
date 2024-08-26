package com.airline_ticket.api.model.dtos.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TicketUpdateDTO(

        @NotNull(message = "ID cannot be null")
        UUID id,

        @NotBlank(message = "Ticket number cannot be blank")
        String ticketNumber,

        @NotNull(message = "Reservation ID cannot be null")
        UUID reservationId,
        @NotNull(message = "Flight ID cannot be null")
        UUID flightId
) {
}