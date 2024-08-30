package com.airline_ticket.api.controller.swagger;

import com.airline_ticket.api.model.dtos.flight.FlightDTO;
import com.airline_ticket.api.model.dtos.flight.FlightRequestDTO;
import com.airline_ticket.api.model.dtos.flight.FlightUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Flights Controller")
public interface FlightControllerInterface {

    @Operation(summary = "Find all flights", description = "Retrieve a list of all flights.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all flights retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<FlightDTO> findAllFlights(@Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find flight by ID", description = "Retrieve a flight by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    FlightDTO findFlightById(@Parameter(description = "ID of the flight to retrieve", required = true) UUID id);

    @Operation(summary = "Find flights by airline", description = "Retrieve flights by airline name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flights by airline retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<FlightDTO> findFlightsByAirline(
            @Parameter(description = "Airline name to search for", required = true) String airline,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find flight by flight number", description = "Retrieve a flight by its flight number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    FlightDTO findFlightByFlightNumber(
            @Parameter(description = "Flight number to search for", required = true) String flightNumber);

    @Operation(summary = "Find flights by location", description = "Retrieve flights by origin and/or destination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flights by location retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<FlightDTO> findFlightsByLocation(
            @Parameter(description = "Origin location to search for", required = false) String origin,
            @Parameter(description = "Destination location to search for", required = false) String destination,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find flights by price", description = "Retrieve flights by price.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flights by price retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<FlightDTO> findFlightsByPrice(
            @Parameter(description = "Price to search for", required = true) Double price,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find flights by departure time", description = "Retrieve flights by departure time range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flights by departure time retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<FlightDTO> findFlightsByDepartureTime(
            @Parameter(description = "Start of departure time range", required = true) ZonedDateTime start,
            @Parameter(description = "End of departure time range", required = true) ZonedDateTime end,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Find flights by arrival time", description = "Retrieve flights by arrival time range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flights by arrival time retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<FlightDTO> findFlightsByArrivalTime(
            @Parameter(description = "Start of arrival time range", required = true) ZonedDateTime start,
            @Parameter(description = "End of arrival time range", required = true) ZonedDateTime end,
            @Parameter(description = "Pagination details", required = true) Pageable pageable);

    @Operation(summary = "Create a new flight", description = "Create a new flight.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Flight created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<FlightDTO> createFlight(
            @Valid @Parameter(description = "Details of the flight to create", required = true) FlightRequestDTO flightRequestDTO);

    @Operation(summary = "Update an existing flight", description = "Update an existing flight.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight updated successfully"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<FlightDTO> updateFlight(
            @Valid @Parameter(description = "Details of the flight to update", required = true) FlightUpdateDTO flightUpdateDTO);

    @Operation(summary = "Delete a flight", description = "Delete a flight by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Flight deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ResponseEntity<Void> deleteFlight(
            @Parameter(description = "ID of the flight to delete", required = true) UUID id);
}