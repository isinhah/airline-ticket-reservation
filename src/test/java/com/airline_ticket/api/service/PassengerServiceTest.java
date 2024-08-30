package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Passenger;
import com.airline_ticket.api.model.dtos.passenger.PassengerDTO;
import com.airline_ticket.api.repository.PassengerRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static com.airline_ticket.api.constants.PassengerConstants.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PassengerServiceTest {

    @InjectMocks
    private PassengerService passengerService;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a page of passengers when successful")
    void getAllPassengers_ReturnsPageOfPassengers_WhenSuccessful() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Passenger> page = new PageImpl<>(Arrays.asList(PASSENGER_1, PASSENGER_2), pageable, 2);

        when(passengerRepository.findAll(pageable)).thenReturn(page);

        Page<PassengerDTO> result = passengerService.getAllPassengers(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().contains(PASSENGER_DTO_1));
        assertTrue(result.getContent().contains(PASSENGER_DTO_2));
    }

    @Test
    @DisplayName("Should return an empty list of passengers inside page object when there are no passengers")
    void getAllPassengers_ReturnsPassengers_WhenThereAreNoPassengers() {
        Page<Passenger> emptyPage = new PageImpl<>(Collections.emptyList());
        Pageable pageable = mock(Pageable.class);

        when(passengerRepository.findAll(pageable)).thenReturn(emptyPage);

        Page<PassengerDTO> result = passengerService.getAllPassengers(pageable);

        Assertions.assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return a passenger by id when successful")
    void getPassengerById_ReturnsPassenger_WhenSuccessful() {
        when(passengerRepository.findById(PASSENGER_1.getId())).thenReturn(Optional.of(PASSENGER_1));

        PassengerDTO result = passengerService.getPassengerById(PASSENGER_1.getId());

        assertNotNull(result);
        assertEquals(PASSENGER_DTO_1, result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when getting a passenger by non-existing id")
    void getPassengerById_ThrowsResourceNotFoundException_WhenPassengerNotFound() {
        when(passengerRepository.findById(PASSENGER_1.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.getPassengerById(PASSENGER_1.getId());
        });

        assertEquals("Passenger not found with id " + PASSENGER_1.getId(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should return a page of passengers by name when successful")
    void getPassengersByName_ReturnsPageOfPassengers_WhenSuccessful() {
        String name = "isabel";
        Pageable pageable = PageRequest.of(0, 10);

        Page<Passenger> passengerPage = new PageImpl<>(Arrays.asList(PASSENGER_1, PASSENGER_2), pageable, 2);
        Page<PassengerDTO> passengerDTOPage = new PageImpl<>(Arrays.asList(PASSENGER_DTO_1, PASSENGER_DTO_2), pageable, 2);

        when(passengerRepository.findByNameContaining(name, pageable)).thenReturn(passengerPage);

        Page<PassengerDTO> result = passengerService.getPassengersByName(name, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(passengerDTOPage, result);
    }

    @Test
    @DisplayName("Should return an empty page when no passengers are found by name")
    void getPassengersByName_ReturnsEmptyPage_WhenNoPassengersFound() {
        String name = "isabel";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Passenger> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(passengerRepository.findByNameContaining(name, pageable)).thenReturn(emptyPage);

        Page<PassengerDTO> result = passengerService.getPassengersByName(name, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return a passenger by phone when successful")
    void getPassengerByPhoneOrEmail_ReturnsPassenger_WhenPhoneExists() {
        when(passengerRepository.findByPhone(PASSENGER_1.getPhone())).thenReturn(Optional.of(PASSENGER_1));

        PassengerDTO result = passengerService.getPassengerByPhoneOrEmail(PASSENGER_1.getPhone(), null);

        assertNotNull(result);
        assertEquals(PASSENGER_DTO_1, result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when getting a passenger by non-existing phone")
    void getPassengerByPhoneOrEmail_ThrowsResourceNotFoundException_WhenPhoneNotFound() {
        when(passengerRepository.findByPhone(PASSENGER_1.getPhone())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.getPassengerByPhoneOrEmail(PASSENGER_1.getPhone(), null);
        });

        assertEquals("Passenger not found with phone " + PASSENGER_1.getPhone(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should return a passenger by email when successful")
    void getPassengerByPhoneOrEmail_ReturnsPassenger_WhenEmailExists() {
        when(passengerRepository.findByEmail(PASSENGER_1.getEmail())).thenReturn(Optional.of(PASSENGER_1));

        PassengerDTO result = passengerService.getPassengerByPhoneOrEmail(null, PASSENGER_1.getEmail());

        assertNotNull(result);
        assertEquals(PASSENGER_DTO_1, result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when getting a passenger by non-existing email")
    void getPassengerByPhoneOrEmail_ThrowsResourceNotFoundException_WhenEmailNotFound() {
        when(passengerRepository.findByEmail(PASSENGER_1.getEmail())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.getPassengerByPhoneOrEmail(null, PASSENGER_1.getEmail());
        });

        assertEquals("Passenger not found with email " + PASSENGER_1.getEmail(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when neither phone nor email is provided")
    void getPassengerByPhoneOrEmail_ThrowsIllegalArgumentException_WhenNeitherProvided() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            passengerService.getPassengerByPhoneOrEmail(null, null);
        });

        assertEquals("At least one parameter (phone or email) must be provided.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should create a new passenger when successful")
    void createPassenger() {
        when(passengerRepository.findByEmail(PASSENGER_REQUEST_DTO.email())).thenReturn(Optional.empty());
        when(passengerRepository.save(any(Passenger.class))).thenReturn(PASSENGER_1);

        PassengerDTO result = passengerService.createPassenger(PASSENGER_REQUEST_DTO);

        assertNotNull(result);
        assertEquals(PASSENGER_DTO_1, result);
    }

    @Test
    @DisplayName("Should throw DataIntegrityViolationException when creating a passenger with an existing email")
    void createPassenger_ThrowsDataIntegrityViolationException_WhenEmailAlreadyExists() {
        when(passengerRepository.findByEmail(PASSENGER_REQUEST_DTO.email())).thenReturn(Optional.of(new Passenger()));

        DataIntegrityViolationException thrown = assertThrows(DataIntegrityViolationException.class, () -> {
            passengerService.createPassenger(PASSENGER_REQUEST_DTO);
        });

        assertEquals("Email already exists.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should update and return a passenger when successful")
    void updatePassenger() {
        when(passengerRepository.findById(PASSENGER_2.getId())).thenReturn(Optional.of(PASSENGER_2));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(PASSENGER_2);

        PassengerDTO result = passengerService.updatePassenger(PASSENGER_UPDATE_DTO);

        assertNotNull(result);
        assertEquals(PASSENGER_DTO_2, result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating a passenger with non-existing id")
    void updatePassenger_ThrowsResourceNotFoundException_WhenPassengerNotFound() {
        when(passengerRepository.findById(PASSENGER_UPDATE_DTO.id())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.updatePassenger(PASSENGER_UPDATE_DTO);
        });

        assertEquals("Passenger not found with id " + PASSENGER_UPDATE_DTO.id(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should delete a passenger when successful")
    void deletePassenger() {
        when(passengerRepository.findById(PASSENGER_1.getId())).thenReturn(Optional.of(PASSENGER_1));

        passengerService.deletePassenger(PASSENGER_1.getId());

        verify(passengerRepository).deleteById(PASSENGER_1.getId());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting a passenger with non-existing id")
    void deletePassenger_ThrowsResourceNotFoundException_WhenPassengerNotFound() {
        when(passengerRepository.findById(PASSENGER_1.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.deletePassenger(PASSENGER_1.getId());
        });

        assertEquals("Passenger not found with id " + PASSENGER_1.getId(), thrown.getMessage());
    }
}