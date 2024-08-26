package com.airline_ticket.api.model.dtos.seat;

import com.airline_ticket.api.model.Seat;
import com.airline_ticket.api.model.dtos.flight.FlightDTO;

import java.util.UUID;

public record SeatDTO(
        UUID id,
        String seatNumber,
        Boolean isAvailable,
        FlightDTO flight
        ) {

    public static SeatDTO seatToDTO(Seat seat) {
        return new SeatDTO(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getAvailable(),
                FlightDTO.flightToDTO(seat.getFlight())
        );
    }
}