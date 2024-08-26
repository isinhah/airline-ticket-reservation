package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Flight;
import com.airline_ticket.api.model.dtos.flight.FlightDTO;
import com.airline_ticket.api.model.dtos.flight.FlightRequestDTO;
import com.airline_ticket.api.model.dtos.flight.FlightUpdateDTO;
import com.airline_ticket.api.repository.FlightRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public Page<FlightDTO> getAllFlights(Pageable pageable) {
        Page<Flight> flightPage = flightRepository.findAll(pageable);
        return flightPage.map(FlightDTO::flightToDTO);
    }

    public FlightDTO getFlightById(UUID id) {
        Flight flight = verifyFlightExistsById(id);
        return FlightDTO.flightToDTO(flight);
    }

    public FlightDTO getFlightByFlightNumber(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with flight number " + flightNumber));
        return FlightDTO.flightToDTO(flight);
    }

    public Page<FlightDTO> getFlightsByAirline(String airline, Pageable pageable) {
        Page<Flight> flightPage = flightRepository.findByAirlineContainingIgnoreCase(airline, pageable);
        return flightPage.map(FlightDTO::flightToDTO);
    }

    public Page<FlightDTO> getFlightsByLocation(String origin, String destination, Pageable pageable) {
        Page<Flight> flights;

        if (origin != null && destination != null) {
            flights = flightRepository.findByOriginAndDestination(origin, destination, pageable);
        } else if (origin != null) {
            flights = flightRepository.findByOriginContainingIgnoreCase(origin, pageable);
        } else if (destination != null) {
            flights = flightRepository.findByDestinationContainingIgnoreCase(destination, pageable);
        } else {
            throw new IllegalArgumentException("At least one parameter (origin or destination) must be provided.");
        }

        return flights.map(FlightDTO::flightToDTO);
    }

    public Page<FlightDTO> getFlightsByDepartureTime(ZonedDateTime start, ZonedDateTime end, Pageable pageable) {
        Page<Flight> flightPage = flightRepository.findByDepartureTimeBetween(start, end, pageable);
        return flightPage.map(FlightDTO::flightToDTO);
    }

    public Page<FlightDTO> getFlightsByArrivalTime(ZonedDateTime start, ZonedDateTime end, Pageable pageable) {
        Page<Flight> flightPage = flightRepository.findByArrivalTimeBetween(start, end, pageable);
        return flightPage.map(FlightDTO::flightToDTO);
    }

    public Page<FlightDTO> getFlightsByPrice(Double price, Pageable pageable) {
        Page<Flight> flightPage = flightRepository.findByPrice(price, pageable);
        return flightPage.map(FlightDTO::flightToDTO);
    }

    @Transactional
    public FlightDTO createFlight(FlightRequestDTO requestDTO) {
        if (flightRepository.findByFlightNumber(requestDTO.flightNumber()).isPresent()) {
            throw new DataIntegrityViolationException("Flight Number already exists.");
        }

        Flight flight = new Flight(
                UUID.randomUUID(),
                requestDTO.airline(),
                requestDTO.flightNumber(),
                requestDTO.origin(),
                requestDTO.destination(),
                requestDTO.departureTime(),
                requestDTO.arrivalTime(),
                requestDTO.price()
        );

        Flight savedFlight = flightRepository.save(flight);
        return FlightDTO.flightToDTO(savedFlight);
    }

    @Transactional
    public FlightDTO updateFlight(FlightUpdateDTO updateDTO) {
        Flight existingFlight = verifyFlightExistsById(updateDTO.id());

        existingFlight.setAirline(updateDTO.airline());
        existingFlight.setFlightNumber(updateDTO.flightNumber());
        existingFlight.setOrigin(updateDTO.origin());
        existingFlight.setDestination(updateDTO.destination());
        existingFlight.setDepartureTime(updateDTO.departureTime());
        existingFlight.setArrivalTime(updateDTO.arrivalTime());
        existingFlight.setPrice(updateDTO.price());

        Flight updatedFlight = flightRepository.save(existingFlight);
        return FlightDTO.flightToDTO(updatedFlight);
    }

    @Transactional
    public void deleteFlight(UUID id) {
        verifyFlightExistsById(id);
        flightRepository.deleteById(id);
    }

    private Flight verifyFlightExistsById(UUID id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + id));
    }
}