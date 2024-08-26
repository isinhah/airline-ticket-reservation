package com.airline_ticket.api.model.dtos.seat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SeatUpdateDTO(

        @NotNull(message = "ID cannot be null")
        UUID id,

        @NotBlank(message = "Seat number cannot be blank")
        String seatNumber,

        @NotNull(message = "Availability cannot be null")
        Boolean isAvailable,

        @NotNull(message = "Flight ID cannot be null")
        UUID flightId
) {
}