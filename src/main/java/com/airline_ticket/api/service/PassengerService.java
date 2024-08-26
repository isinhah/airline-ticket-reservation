package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Passenger;
import com.airline_ticket.api.model.dtos.passenger.PassengerDTO;
import com.airline_ticket.api.model.dtos.passenger.PassengerRequestDTO;
import com.airline_ticket.api.model.dtos.passenger.PassengerUpdateDTO;
import com.airline_ticket.api.repository.PassengerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public Page<PassengerDTO> getAllPassengers(Pageable pageable) {
        Page<Passenger> passengerPage = passengerRepository.findAll(pageable);
        return passengerPage.map(PassengerDTO::passengerToDto);
    }

    public PassengerDTO getPassengerById(UUID id) {
        Passenger passenger = verifyPassengerExistsById(id);
        return PassengerDTO.passengerToDto(passenger);
    }

    public Page<PassengerDTO> getPassengersByName(String name, Pageable pageable) {
        Page<Passenger> passengerPage = passengerRepository.findByNameContaining(name, pageable);
        return passengerPage.map(PassengerDTO::passengerToDto);
    }

    public PassengerDTO getPassengerByPhoneOrEmail(String phone, String email) {
        if (phone != null) {
            Passenger passenger = passengerRepository.findByPhone(phone)
                    .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with phone " + phone));
            return PassengerDTO.passengerToDto(passenger);
        }

        if (email != null) {
            Passenger passenger = passengerRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with email " + email));
            return PassengerDTO.passengerToDto(passenger);
        }

        throw new IllegalArgumentException("At least one parameter (phone or email) must be provided.");
    }

    @Transactional
    public PassengerDTO createPassenger(PassengerRequestDTO requestDTO) {
        if (passengerRepository.findByEmail(requestDTO.email()).isPresent()) {
            throw new DataIntegrityViolationException("Email already exists.");
        }

        Passenger passenger = new Passenger(
                UUID.randomUUID(),
                requestDTO.name(),
                requestDTO.email(),
                requestDTO.password(),
                requestDTO.phone()
        );
        Passenger savedPassenger = passengerRepository.save(passenger);
        return PassengerDTO.passengerToDto(savedPassenger);
    }

    @Transactional
    public PassengerDTO updatePassenger(PassengerUpdateDTO updateDTO) {
        Passenger existingPassenger = verifyPassengerExistsById(updateDTO.id());

        existingPassenger.setName(updateDTO.name());
        existingPassenger.setEmail(updateDTO.email());
        existingPassenger.setPassword(updateDTO.password());
        existingPassenger.setPhone(updateDTO.phone());

        Passenger updatedPassenger = passengerRepository.save(existingPassenger);
        return PassengerDTO.passengerToDto(updatedPassenger);
    }

    @Transactional
    public void deletePassenger(UUID id) {
        verifyPassengerExistsById(id);
        passengerRepository.deleteById(id);
    }

    private Passenger verifyPassengerExistsById(UUID id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id " + id));
    }
}