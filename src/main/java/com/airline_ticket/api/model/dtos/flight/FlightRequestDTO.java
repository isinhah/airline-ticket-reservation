package com.airline_ticket.api.model.dtos.flight;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.ZonedDateTime;

public record FlightRequestDTO(
        @NotBlank(message = "Airline cannot be blank")
        String airline,

        @NotBlank(message = "Flight number cannot be blank")
        String flightNumber,

        @NotBlank(message = "Origin cannot be blank")
        String origin,

        @NotBlank(message = "Destination cannot be blank")
        String destination,

        @NotNull(message = "Departure time cannot be null")
        ZonedDateTime departureTime,

        @NotNull(message = "Arrival time cannot be null")
        ZonedDateTime arrivalTime,

        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be a positive value")
        Double price
) {
}
