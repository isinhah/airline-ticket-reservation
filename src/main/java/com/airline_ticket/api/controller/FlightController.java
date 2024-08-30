package com.airline_ticket.api.controller;

import com.airline_ticket.api.controller.swagger.FlightControllerInterface;
import com.airline_ticket.api.model.dtos.flight.FlightDTO;
import com.airline_ticket.api.model.dtos.flight.FlightRequestDTO;
import com.airline_ticket.api.model.dtos.flight.FlightUpdateDTO;
import com.airline_ticket.api.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/flights")
public class FlightController implements FlightControllerInterface {

    @Autowired
    private FlightService flightService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FlightDTO> findAllFlights(@PageableDefault(size = 10) Pageable pageable) {
        return flightService.getAllFlights(pageable).getContent();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FlightDTO findFlightById(@PathVariable UUID id) {
        return flightService.getFlightById(id);
    }

    @GetMapping("/searchByAirline")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightDTO> findFlightsByAirline(@RequestParam String airline, @PageableDefault(size = 10) Pageable pageable) {
        return flightService.getFlightsByAirline(airline, pageable).getContent();
    }

    @GetMapping("/searchByFlightNumber")
    @ResponseStatus(HttpStatus.OK)
    public FlightDTO findFlightByFlightNumber(@RequestParam String flightNumber) {
        return flightService.getFlightByFlightNumber(flightNumber);
    }

    @GetMapping("/searchByLocation")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightDTO> findFlightsByLocation(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @PageableDefault(size = 10) Pageable pageable) {
        return flightService.getFlightsByLocation(origin, destination, pageable).getContent();
    }

    @GetMapping("/searchByPrice")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightDTO> findFlightsByPrice(@RequestParam Double price, @PageableDefault(size = 10) Pageable pageable) {
        return flightService.getFlightsByPrice(price, pageable).getContent();
    }

    @GetMapping("/searchByDepartureTime")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightDTO> findFlightsByDepartureTime(@RequestParam ZonedDateTime start,
                                                      @RequestParam ZonedDateTime end,
                                                      @PageableDefault(size = 10) Pageable pageable) {
        return flightService.getFlightsByDepartureTime(start, end, pageable).getContent();
    }

    @GetMapping("/searchByArrivalTime")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightDTO> findFlightsByArrivalTime(@RequestParam ZonedDateTime start,
                                                    @RequestParam ZonedDateTime end,
                                                    @PageableDefault(size = 10) Pageable pageable) {
        return flightService.getFlightsByArrivalTime(start, end, pageable).getContent();
    }

    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(@Valid  @RequestBody FlightRequestDTO flightRequestDTO) {
        FlightDTO createdFlight = flightService.createFlight(flightRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
    }

    @PutMapping
    public ResponseEntity<FlightDTO> updateFlight(@Valid @RequestBody FlightUpdateDTO flightUpdateDTO) {
        FlightDTO updatedFlight = flightService.updateFlight(flightUpdateDTO);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable UUID id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
