package com.airline_ticket.api.model.dtos.flight;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.ZonedDateTime;

@Schema(name = "Flight Request DTO", description = "DTO for creating a flight")
public record FlightRequestDTO(
        @Schema(description = "Name of the airline operating the flight.", example = "Delta Airlines")
        @NotBlank(message = "Airline cannot be blank")
        String airline,

        @Schema(description = "Flight number assigned to the flight.", example = "DL123")
        @NotBlank(message = "Flight number cannot be blank")
        String flightNumber,

        @Schema(description = "The origin city of the flight.", example = "London")
        @NotBlank(message = "Origin cannot be blank")
        String origin,

        @Schema(description = "The destination city of the flight.", example = "Tokyo")
        @NotBlank(message = "Destination cannot be blank")
        String destination,

        @Schema(description = "The scheduled departure time of the flight.", example = "2024-12-01T15:30:00Z")
        @NotNull(message = "Departure time cannot be null")
        ZonedDateTime departureTime,

        @Schema(description = "The scheduled arrival time of the flight.", example = "2024-12-01T18:45:00Z")
        @NotNull(message = "Arrival time cannot be null")
        ZonedDateTime arrivalTime,

        @Schema(description = "The price of the flight in USD.", example = "299.99")
        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be a positive value")
        Double price
) {
}
