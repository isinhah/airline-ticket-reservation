package com.airline_ticket.api.model.dtos.seat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "Seat Request DTO", description = "DTO for requesting a seat")
public record SeatRequestDTO(
        @Schema(description = "Seat number or identifier.", example = "12A")
        @NotBlank(message = "Seat number cannot be blank")
        String seatNumber,

        @Schema(description = "Indicates whether the seat is available for reservation.", example = "true")
        @Nullable
        Boolean isAvailable,

        @Schema(description = "Unique identifier for the flight to which this seat belongs.", example = "f1d2d2d2-2b5f-4a3b-9c4d-e0e1f2g3h4i5")
        @NotNull(message = "Flight ID cannot be null")
        UUID flightId
) {
}