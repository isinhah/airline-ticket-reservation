package com.airline_ticket.api.model.dtos.passenger;

import com.airline_ticket.api.model.Passenger;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public record PassengerDTO(
        UUID id,
        String name,
        String email,
        @JsonIgnore String password,
        String phone
) {
    public static PassengerDTO passengerToDto(Passenger passenger) {
        return new PassengerDTO(
                passenger.getId(),
                passenger.getName(),
                passenger.getEmail(),
                passenger.getPassword(),
                passenger.getPhone()
        );
    }
}