package com.airline_ticket.api.model.dtos.passenger;

import com.airline_ticket.api.model.Passenger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "Passenger DTO", description = "DTO for representing a passenger")
public record PassengerDTO(
        @Schema(description = "Unique identifier for the passenger.", example = "e4d2f8f6-234c-4c9a-bd3b-4e1c5b9c1a0a")
        UUID id,

        @Schema(description = "Name of the passenger.", example = "Isabel")
        String name,

        @Schema(description = "Email of the passenger.", example = "isabel@example.com")
        String email,

        @Schema(description = "Password of the passenger (not included in responses).")
        @JsonIgnore
        String password,

        @Schema(description = "Phone number of the passenger.", example = "+1987654321")
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