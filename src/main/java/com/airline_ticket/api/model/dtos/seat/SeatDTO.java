package com.airline_ticket.api.model.dtos.seat;

import com.airline_ticket.api.model.Seat;
import com.airline_ticket.api.model.dtos.flight.FlightDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "Seat DTO", description = "DTO for representing a seat")
public record SeatDTO(
        @Schema(description = "Unique identifier for the seat.", example = "f1d2d2d2-2b5f-4a3b-9c4d-e0e1f2g3h4i5")
        UUID id,

        @Schema(description = "Seat number or identifier.", example = "12A")
        String seatNumber,

        @Schema(description = "Indicates whether the seat is available for reservation.", example = "true")
        Boolean isAvailable,

        @Schema(description = "Flight to which this seat belongs.")
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