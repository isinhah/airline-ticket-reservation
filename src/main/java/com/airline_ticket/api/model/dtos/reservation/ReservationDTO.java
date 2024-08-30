package com.airline_ticket.api.model.dtos.reservation;

import com.airline_ticket.api.model.Reservation;
import com.airline_ticket.api.model.dtos.passenger.PassengerDTO;
import com.airline_ticket.api.model.dtos.seat.SeatDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

@Schema(name = "Reservation DTO", description = "DTO for representing a reservation")
public record ReservationDTO(

        @Schema(description = "Unique identifier for the reservation.", example = "f1d2d2d2-2b5f-4a3b-9c4d-e0e1f2g3h4i5")
        @NotNull(message = "ID cannot be null")
        UUID id,

        @Schema(description = "Date and time when the reservation was made.", example = "2024-08-30T14:30:00Z")
        @NotNull(message = "Reservation date cannot be null")
        ZonedDateTime reservationDate,

        @Schema(description = "Details of the seat reserved.")
        @NotNull(message = "Seat details cannot be null")
        SeatDTO seat,

        @Schema(description = "Details of the passenger who made the reservation.")
        @NotNull(message = "Passenger details cannot be null")
        PassengerDTO passenger
        ) {

    public static ReservationDTO toReservationDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getReservationDate(),
                SeatDTO.seatToDTO(reservation.getSeat()),
                PassengerDTO.passengerToDto(reservation.getPassenger())
        );
    }
}