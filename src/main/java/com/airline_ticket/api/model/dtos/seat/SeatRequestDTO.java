package com.airline_ticket.api.model.dtos.seat;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SeatRequestDTO(
        @NotBlank(message = "Seat number cannot be blank")
        String seatNumber,

        @Nullable
        Boolean isAvailable,

        @NotNull(message = "Flight ID cannot be null")
        UUID flightId
) {
}