package com.airline_ticket.api.model.dtos.flight;

import com.airline_ticket.api.model.Flight;

import java.time.ZonedDateTime;
import java.util.UUID;

public record FlightDTO(
        UUID id,
        String airline,
        String flightNumber,
        String origin,
        String destination,
        ZonedDateTime departureTime,
        ZonedDateTime arrivalTime,
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