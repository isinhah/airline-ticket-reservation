package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Flight;
import com.airline_ticket.api.model.dtos.flight.FlightDTO;
import com.airline_ticket.api.repository.FlightRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.airline_ticket.api.constants.FlightConstants.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FlightServiceTest {

    @InjectMocks
    private FlightService flightService;
    @Mock
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a flight by id when successful")
    void getFlightById_ReturnsFlight_WhenSuccessful() {
        when(flightRepository.findById(FLIGHT_1.getId())).thenReturn(Optional.of(FLIGHT_1));

        FlightDTO result = flightService.getFlightById(FLIGHT_1.getId());

        assertNotNull(result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when getting a flight by non-existing id")
    void getFlightById_ThrowsResourceNotFoundException_WhenFlightNotFound() {
        when(flightRepository.findById(FLIGHT_1.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            flightService.getFlightById(FLIGHT_1.getId());
        });

        assertEquals("Flight not found with id " + FLIGHT_1.getId(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should return a flight by flight number when successful")
    void getFlightByFlightNumber_ReturnsFlight_WhenFlightNumberExists() {
        when(flightRepository.findByFlightNumber(FLIGHT_1.getFlightNumber())).thenReturn(Optional.of(FLIGHT_1));

        FlightDTO result = flightService.getFlightByFlightNumber(FLIGHT_1.getFlightNumber());

        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when getting a flight by non-existing number")
    void getFlightByFlightNumber_ThrowsResourceNotFoundException_WhenFlightNumberNotFound() {
        when(flightRepository.findByFlightNumber(FLIGHT_1.getFlightNumber())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            flightService.getFlightByFlightNumber(FLIGHT_1.getFlightNumber());
        });

        assertEquals("Flight not found with flight number " + FLIGHT_1.getFlightNumber(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should return a list of flights by airline inside page object when successful")
    void getFlightByAirline_ReturnsFlights_WhenSuccessful() {
        String airline = "Global";
        Pageable pageable = PageRequest.of(0, 10);

        Page<Flight> page = new PageImpl<>(Arrays.asList(FLIGHT_1, FLIGHT_2), pageable, 2);
        Page<FlightDTO> expectedPageDTO = new PageImpl<>(Arrays.asList(FLIGHT_DTO_1, FLIGHT_DTO_2), pageable, 2);

        when(flightRepository.findByAirlineContainingIgnoreCase(airline, pageable)).thenReturn(page);

        Page<FlightDTO> result = flightService.getFlightsByAirline(airline, pageable);

        Assertions.assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    @DisplayName("Should return an empty page when no flights are found by airline")
    void getFlightsByAirline_ReturnsEmptyPage_WhenAirlineNotFound() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Flight> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(flightRepository.findByAirlineContainingIgnoreCase(FLIGHT_1.getAirline(), pageable)).thenReturn(emptyPage);

        Page<FlightDTO> result = flightService.getFlightsByAirline(FLIGHT_1.getAirline(), pageable);

        Assertions.assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return flights when both origin and destination are provided")
    void getFlightsByLocation_WhenOriginAndDestinationAreProvided_ShouldReturnFlights() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Flight> page = new PageImpl<>(List.of(FLIGHT_1));

        when(flightRepository.findByOriginAndDestination(FLIGHT_1.getOrigin(), FLIGHT_1.getDestination(), pageable)).thenReturn(page);

        Page<FlightDTO> result = flightService.getFlightsByLocation(FLIGHT_1.getOrigin(), FLIGHT_1.getDestination(), pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Should return flights when only origin is provided")
    void getFlightsByLocation_WhenOnlyOriginProvided_ShouldReturnFlights() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Flight> flightPage = new PageImpl<>(List.of(FLIGHT_1));

        when(flightRepository.findByOriginContainingIgnoreCase(FLIGHT_1.getOrigin(), pageable))
                .thenReturn(flightPage);

        Page<FlightDTO> result = flightService.getFlightsByLocation(FLIGHT_1.getOrigin(), null, pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Should return flights when only destination is provided")
    void getFlightsByLocation_WhenOnlyDestinationProvided_ShouldReturnFlights() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Flight> flightPage = new PageImpl<>(List.of(FLIGHT_1));

        when(flightRepository.findByDestinationContainingIgnoreCase(FLIGHT_1.getDestination(), pageable))
                .thenReturn(flightPage);

        Page<FlightDTO> result = flightService.getFlightsByLocation(null, FLIGHT_1.getDestination(), pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when neither origin nor destination is provided")
    void getFlightsByLocation_WhenNoOriginOrDestinationProvided_ShouldThrowException() {
        Pageable pageable = PageRequest.of(0, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            flightService.getFlightsByLocation(null, null, pageable);
        });
    }

    @Test
    @DisplayName("Should return flights within the specified departure time")
    void getFlightsByDepartureTime_WhenWithinRange_ShouldReturnFlights() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Flight> flightPage = new PageImpl<>(List.of(FLIGHT_1, FLIGHT_2));

        when(flightRepository.findByDepartureTimeBetween(FLIGHT_1.getDepartureTime(), FLIGHT_2.getDepartureTime(), pageable))
                .thenReturn(flightPage);

        Page<FlightDTO> result = flightService.getFlightsByDepartureTime(FLIGHT_1.getDepartureTime(), FLIGHT_2.getDepartureTime(), pageable);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    @DisplayName("Should return flights within the specified arrival time")
    void getFlightsByArrivalTime_WhenWithinRange_ShouldReturnFlights() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Flight> flightPage = new PageImpl<>(List.of(FLIGHT_1, FLIGHT_2));

        when(flightRepository.findByArrivalTimeBetween(FLIGHT_1.getArrivalTime(), FLIGHT_2.getArrivalTime(), pageable))
                .thenReturn(flightPage);

        Page<FlightDTO> result = flightService.getFlightsByArrivalTime(FLIGHT_1.getArrivalTime(), FLIGHT_2.getArrivalTime(), pageable);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    @DisplayName("Should create a new flight when flight number is unique")
    void createFlight() {
        when(flightRepository.findByFlightNumber(FLIGHT_REQUEST_DTO.flightNumber()))
                .thenReturn(Optional.empty());
        when(flightRepository.save(any(Flight.class)))
                .thenReturn(FLIGHT_1);

        FlightDTO result = flightService.createFlight(FLIGHT_REQUEST_DTO);

        assertNotNull(result);
        assertEquals(FLIGHT_1.getFlightNumber(), result.flightNumber());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    @DisplayName("Should throw DataIntegrityViolationException when flight number already exists")
    void createFlight_WhenFlightNumberAlreadyExists_ShouldThrowException() {
        when(flightRepository.findByFlightNumber(FLIGHT_REQUEST_DTO.flightNumber()))
                .thenReturn(Optional.of(FLIGHT_1));

        assertThrows(DataIntegrityViolationException.class, () -> flightService.createFlight(FLIGHT_REQUEST_DTO));
        verify(flightRepository, never()).save(any(Flight.class));
    }

    @Test
    @DisplayName("Should update an existing flight")
    void updateFlight() {
        when(flightRepository.findById(FLIGHT_UPDATE_DTO.id()))
                .thenReturn(Optional.of(FLIGHT_1));
        when(flightRepository.save(any(Flight.class)))
                .thenReturn(FLIGHT_1);

        FlightDTO result = flightService.updateFlight(FLIGHT_UPDATE_DTO);

        assertNotNull(result);
        assertEquals(FLIGHT_UPDATE_DTO.flightNumber(), result.flightNumber());
        verify(flightRepository).save(FLIGHT_1);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when flight to update does not exist")
    void updateFlight_WhenFlightDoesNotExist_ShouldThrowException() {
        when(flightRepository.findById(FLIGHT_UPDATE_DTO.id()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> flightService.updateFlight(FLIGHT_UPDATE_DTO));
        verify(flightRepository, never()).save(any(Flight.class));
    }

    @Test
    @DisplayName("Should delete a flight when successful")
    void deleteFlight() {
        when(flightRepository.findById(FLIGHT_1.getId())).thenReturn(Optional.of(FLIGHT_1));

        flightService.deleteFlight(FLIGHT_1.getId());

        verify(flightRepository).deleteById(FLIGHT_1.getId());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when flight to delete does not exist")
    void deleteFlight_WhenFlightDoesNotExist_ShouldThrowException() {
        when(flightRepository.findById(FLIGHT_1.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> flightService.deleteFlight(FLIGHT_1.getId()));
    }
}