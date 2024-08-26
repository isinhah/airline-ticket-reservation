package com.airline_ticket.api.model.dtos.reservation;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationUpdateDTO(

        @NotNull(message = "ID cannot be null")
        UUID id,

        @NotNull(message = "Seat ID cannot be null")
        UUID seatId,

        @NotNull(message = "Passenger ID cannot be null")
        UUID passengerId
) {
}