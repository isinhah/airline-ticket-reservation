package com.airline_ticket.api.constants;

import com.airline_ticket.api.model.Flight;
import com.airline_ticket.api.model.dtos.employee.EmployeeDTO;
import com.airline_ticket.api.model.dtos.flight.FlightDTO;
import com.airline_ticket.api.model.dtos.flight.FlightRequestDTO;
import com.airline_ticket.api.model.dtos.flight.FlightUpdateDTO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class FlightConstants {
    private static final UUID FLIGHT_ID_1 = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID FLIGHT_ID_2 = UUID.fromString("22222222-2222-2222-2222-222222222222");

    public static final Flight FLIGHT_1 = new Flight(
            FLIGHT_ID_1,
            "Global Airways",
            "AW123",
            "New York",
            "London",
            ZonedDateTime.parse("2024-08-28T10:15:30+01:00[Europe/London]"),
            ZonedDateTime.parse("2024-08-28T14:45:30+01:00[Europe/London]"),
            899.99
    );

    public static final Flight FLIGHT_2 = new Flight(
            FLIGHT_ID_2,
            "Global Airlines",
            "GA456",
            "Tokyo",
            "San Francisco",
            ZonedDateTime.parse("2024-09-15T22:30:00+09:00[Asia/Tokyo]"),
            ZonedDateTime.parse("2024-09-15T16:00:00-07:00[America/Los_Angeles]"),
            1200.50
    );

    public static final FlightDTO FLIGHT_DTO_1 = new FlightDTO(
            FLIGHT_1.getId(),
            FLIGHT_1.getAirline(),
            FLIGHT_1.getFlightNumber(),
            FLIGHT_1.getOrigin(),
            FLIGHT_1.getDestination(),
            FLIGHT_1.getDepartureTime(),
            FLIGHT_1.getArrivalTime(),
            FLIGHT_1.getPrice());
    public static final FlightDTO FLIGHT_DTO_2 = new FlightDTO(
            FLIGHT_2.getId(),
            FLIGHT_2.getAirline(),
            FLIGHT_2.getFlightNumber(),
            FLIGHT_2.getOrigin(),
            FLIGHT_2.getDestination(),
            FLIGHT_2.getDepartureTime(),
            FLIGHT_2.getArrivalTime(),
            FLIGHT_2.getPrice());

    public static final FlightRequestDTO FLIGHT_REQUEST_DTO = new FlightRequestDTO(
            FLIGHT_1.getAirline(),
            FLIGHT_1.getFlightNumber(),
            FLIGHT_1.getOrigin(),
            FLIGHT_1.getDestination(),
            FLIGHT_1.getDepartureTime(),
            FLIGHT_1.getArrivalTime(),
            FLIGHT_1.getPrice()
    );
    public static final FlightUpdateDTO FLIGHT_UPDATE_DTO = new FlightUpdateDTO(FLIGHT_2.getId(), FLIGHT_2.getAirline(),
            FLIGHT_2.getFlightNumber(),
            FLIGHT_2.getOrigin(),
            FLIGHT_2.getDestination(),
            FLIGHT_2.getDepartureTime(),
            FLIGHT_2.getArrivalTime(),
            FLIGHT_2.getPrice());

    public static final List<FlightDTO> FLIGHTS = List.of(FLIGHT_DTO_1, FLIGHT_DTO_2);
}
