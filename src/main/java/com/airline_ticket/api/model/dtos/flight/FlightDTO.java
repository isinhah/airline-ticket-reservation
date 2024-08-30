package com.airline_ticket.api.model.dtos.flight;

import com.airline_ticket.api.model.Flight;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.UUID;

@Schema(name = "FlightDTO", description = "DTO for Flight")
public record FlightDTO(
        @Schema(description = "Unique identifier for the flight.", example = "b2c3e7d5-6789-4abc-1234-56789abcdef0")
        UUID id,

        @Schema(description = "Name of the airline operating the flight.", example = "Delta Airlines")
        String airline,

        @Schema(description = "Flight number assigned to the flight.", example = "DL123")
        String flightNumber,

        @Schema(description = "The origin city of the flight.", example = "London")
        String origin,

        @Schema(description = "The destination city of the flight.", example = "Tokyo")
        String destination,

        @Schema(description = "The scheduled departure time of the flight.", example = "2024-12-01T15:30:00Z")
        ZonedDateTime departureTime,

        @Schema(description = "The scheduled arrival time of the flight.", example = "2024-12-01T18:45:00Z")
        ZonedDateTime arrivalTime,

        @Schema(description = "The price of the flight in USD.", example = "299.99")
        Double price
) {

    public static FlightDTO flightToDTO(Flight flight) {
        return new FlightDTO(
                flight.getId(),
                flight.getAirline(),
                flight.getFlightNumber(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getPrice()
        );
    }
}