package com.airline_ticket.api.model.dtos.reservation;

import com.airline_ticket.api.model.Reservation;
import com.airline_ticket.api.model.dtos.passenger.PassengerDTO;
import com.airline_ticket.api.model.dtos.seat.SeatDTO;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ReservationDTO(
        @NotNull UUID id,
        @NotNull ZonedDateTime reservationDate,
        @NotNull SeatDTO seat,
        @NotNull PassengerDTO passenger
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