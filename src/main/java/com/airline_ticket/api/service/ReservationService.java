package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Passenger;
import com.airline_ticket.api.model.Reservation;
import com.airline_ticket.api.model.Seat;
import com.airline_ticket.api.model.Ticket;
import com.airline_ticket.api.model.dtos.reservation.ReservationDTO;
import com.airline_ticket.api.model.dtos.reservation.ReservationRequestDTO;
import com.airline_ticket.api.model.dtos.reservation.ReservationUpdateDTO;
import com.airline_ticket.api.repository.PassengerRepository;
import com.airline_ticket.api.repository.ReservationRepository;
import com.airline_ticket.api.repository.SeatRepository;
import com.airline_ticket.api.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;

    public ReservationService(ReservationRepository reservationRepository, SeatRepository seatRepository, PassengerRepository passengerRepository, TicketRepository ticketRepository) {
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
        this.passengerRepository = passengerRepository;
        this.ticketRepository = ticketRepository;
    }

    public Page<ReservationDTO> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(ReservationDTO::toReservationDTO);
    }

    public ReservationDTO getReservationById(UUID id) {
        Reservation reservation = verifyReservationExistsById(id);
        return ReservationDTO.toReservationDTO(reservation);
    }

    public Page<ReservationDTO> getReservationsByDate(String reservationDateStr, Pageable pageable) {
        ZonedDateTime reservationDate;

        try {
            reservationDate = ZonedDateTime.parse(reservationDateStr);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use ISO 8601 format.");
        }

        return reservationRepository.findByReservationDate(reservationDate, pageable)
                .map(ReservationDTO::toReservationDTO);
    }

    public Page<ReservationDTO> getReservations(UUID seatId, UUID passengerId, Pageable pageable) {
        Page<Reservation> reservations;

        if (seatId != null && passengerId != null) {
            reservations = reservationRepository.findBySeatIdAndPassengerId(seatId, passengerId, pageable);
        } else if (seatId != null) {
            reservations = reservationRepository.findBySeatId(seatId, pageable);
        } else if (passengerId != null) {
            reservations = reservationRepository.findByPassengerId(passengerId, pageable);
        } else {
            throw new IllegalArgumentException("At least one parameter (seatId or passengerId) must be provided.");
        }

        return reservations.map(ReservationDTO::toReservationDTO);
    }

    @Transactional
    public ReservationDTO createReservation(ReservationRequestDTO requestDTO) {
        Seat seat = seatRepository.findById(requestDTO.seatId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id " + requestDTO.seatId()));

        Passenger passenger = passengerRepository.findById(requestDTO.passengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id " + requestDTO.passengerId()));

        if (!seat.getAvailable()) {
            throw new IllegalStateException("Seat with id " + requestDTO.seatId() + " is not available.");
        }

        Reservation reservation = new Reservation(
                UUID.randomUUID(),
                ZonedDateTime.now(),
                seat,
                passenger
        );
        Reservation savedReservation = reservationRepository.save(reservation);

        Ticket ticket = new Ticket(
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                savedReservation,
                savedReservation.getSeat().getFlight()
        );
        ticketRepository.save(ticket);

        seat.setAvailable(false);
        seatRepository.save(seat);

        return ReservationDTO.toReservationDTO(savedReservation);
    }


    @Transactional
    public ReservationDTO updateReservation(ReservationUpdateDTO updateDTO) {
        Reservation existingReservation = verifyReservationExistsById(updateDTO.id());

        Seat newSeat = seatRepository.findById(updateDTO.seatId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id " + updateDTO.seatId()));

        Passenger newPassenger = passengerRepository.findById(updateDTO.passengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id " + updateDTO.passengerId()));

        existingReservation.setSeat(newSeat);
        existingReservation.setPassenger(newPassenger);

        Reservation updatedReservation = reservationRepository.save(existingReservation);

        return ReservationDTO.toReservationDTO(updatedReservation);
    }

    public void deleteReservation(UUID id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation not found with id " + id);
        }
        reservationRepository.deleteById(id);
    }

    private Reservation verifyReservationExistsById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + id));
    }
}
