package com.airline_ticket.api.model.dtos.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "Reservation Update DTO", description = "DTO for updating a reservation")
public record ReservationUpdateDTO(

        @Schema(description = "Unique identifier for the reservation.", example = "f1d2d2d2-2b5f-4a3b-9c4d-e0e1f2g3h4i5", required = true)
        @NotNull(message = "ID cannot be null")
        UUID id,

        @Schema(description = "Unique identifier for the seat being reserved.", example = "f1d2d2d2-2b5f-4a3b-9c4d-e0e1f2g3h4i5", required = true)
        @NotNull(message = "Seat ID cannot be null")
        UUID seatId,

        @Schema(description = "Unique identifier for the passenger making the reservation.", example = "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6", required = true)
        @NotNull(message = "Passenger ID cannot be null")
        UUID passengerId
) {}