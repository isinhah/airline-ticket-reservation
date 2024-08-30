package com.airline_ticket.api.model.dtos.seat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "Seat Update DTO", description = "DTO for updating seat information")
public record SeatUpdateDTO(

        @Schema(description = "Unique identifier for the seat.", example = "f1d2d2d2-2b5f-4a3b-9c4d-e0e1f2g3h4i5", required = true)
        @NotNull(message = "ID cannot be null")
        UUID id,

        @Schema(description = "Seat number or identifier.", example = "12A", required = true)
        @NotBlank(message = "Seat number cannot be blank")
        String seatNumber,

        @Schema(description = "Indicates whether the seat is available for reservation.", example = "true", required = true)
        @NotNull(message = "Availability cannot be null")
        Boolean isAvailable,

        @Schema(description = "Unique identifier for the flight to which this seat belongs.", example = "f1d2d2d2-2b5f-4a3b-9c4d-e0e1f2g3h4i5", required = true)
        @NotNull(message = "Flight ID cannot be null")
        UUID flightId
) {
}