package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Flight;
import com.airline_ticket.api.model.Seat;
import com.airline_ticket.api.model.dtos.seat.SeatDTO;
import com.airline_ticket.api.model.dtos.seat.SeatRequestDTO;
import com.airline_ticket.api.model.dtos.seat.SeatUpdateDTO;
import com.airline_ticket.api.repository.FlightRepository;
import com.airline_ticket.api.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;

    public SeatService(SeatRepository seatRepository, FlightRepository flightRepository) {
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
    }

    public Page<SeatDTO> getAllSeats(Pageable pageable) {
        Page<Seat> seatPage = seatRepository.findAll(pageable);
        return seatPage.map(SeatDTO::seatToDTO);
    }

    public SeatDTO getSeatById(UUID id) {
        Seat seat = verifySeatExistsById(id);
        return SeatDTO.seatToDTO(seat);
    }

    public Page<SeatDTO> getAvailableSeatsByFlight(UUID flightId, Boolean isAvailable, Pageable pageable) {
         Page<Seat> seatPage = seatRepository.findByFlightIdAndIsAvailable(flightId, isAvailable, pageable);
         return seatPage.map(SeatDTO::seatToDTO);
    }

    public Page<SeatDTO> getSeats(String seatNumber, UUID flightId, Pageable pageable) {
        Page<Seat> seats;

        if (seatNumber != null && flightId != null) {
            seats = seatRepository.findBySeatNumberAndFlightId(seatNumber, flightId, pageable);
        } else if (seatNumber != null) {
            seats = seatRepository.findBySeatNumber(seatNumber, pageable);
        } else if (flightId != null) {
            seats = seatRepository.findByFlightId(flightId, pageable);
        } else {
            throw new IllegalArgumentException("At least one parameter (seatNumber or flightId) must be provided.");
        }

        return seats.map(SeatDTO::seatToDTO);
    }

    @Transactional
    public SeatDTO createSeat(SeatRequestDTO requestDTO) {
        Flight flight = flightRepository.findById(requestDTO.flightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + requestDTO.flightId()));

        Seat seat = new Seat(
                requestDTO.seatNumber(),
                requestDTO.isAvailable() != null ? requestDTO.isAvailable() : true,
                flight
        );

        Seat savedSeat = seatRepository.save(seat);
        return SeatDTO.seatToDTO(savedSeat);
    }

    @Transactional
    public SeatDTO updateSeat(SeatUpdateDTO updateDTO) {
        Seat existingSeat = verifySeatExistsById(updateDTO.id());

        Flight flight = flightRepository.findById(updateDTO.flightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + updateDTO.flightId()));

        existingSeat.setSeatNumber(updateDTO.seatNumber());
        existingSeat.setAvailable(updateDTO.isAvailable());
        existingSeat.setFlight(flight);

        Seat updatedSeat = seatRepository.save(existingSeat);
        return SeatDTO.seatToDTO(updatedSeat);
    }

    @Transactional
    public void deleteSeat(UUID id) {
        verifySeatExistsById(id);
        seatRepository.deleteById(id);
    }

    private Seat verifySeatExistsById(UUID id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id " + id));
    }
}
