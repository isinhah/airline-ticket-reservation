package com.airline_ticket.api.model.dtos.ticket;

import com.airline_ticket.api.model.Ticket;
import com.airline_ticket.api.model.dtos.flight.FlightDTO;
import com.airline_ticket.api.model.dtos.reservation.ReservationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "Ticket DTO", description = "DTO for representing a ticket")
public record TicketDTO(

        @Schema(description = "Unique identifier for the ticket.", example = "e7d7e6f5-1c4d-41b4-851e-c8d9e12c5b5e")
        UUID id,

        @Schema(description = "Unique ticket number.", example = "TICK123456")
        String ticketNumber,

        @Schema(description = "Unique identifier for the reservation associated with this ticket.", example = "b2c1a3d4-567e-890f-ab12-cd34ef567890")
        UUID reservationId,

        @Schema(description = "Flight details associated with this ticket.")
        FlightDTO flight
) {
    public static TicketDTO ticketToDTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getTicketNumber(),
                ticket.getReservation().getId(),
                FlightDTO.flightToDTO(ticket.getFlight())
        );
    }
}