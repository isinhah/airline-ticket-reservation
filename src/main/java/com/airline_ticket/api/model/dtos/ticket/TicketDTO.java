package com.airline_ticket.api.model.dtos.ticket;

import com.airline_ticket.api.model.Ticket;
import com.airline_ticket.api.model.dtos.flight.FlightDTO;
import com.airline_ticket.api.model.dtos.reservation.ReservationDTO;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TicketDTO(
        UUID id,
        String ticketNumber,
        UUID reservationId,
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