package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Flight;
import com.airline_ticket.api.model.Reservation;
import com.airline_ticket.api.model.Ticket;
import com.airline_ticket.api.model.dtos.ticket.TicketDTO;
import com.airline_ticket.api.model.dtos.ticket.TicketRequestDTO;
import com.airline_ticket.api.model.dtos.ticket.TicketUpdateDTO;
import com.airline_ticket.api.repository.FlightRepository;
import com.airline_ticket.api.repository.ReservationRepository;
import com.airline_ticket.api.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;

    public TicketService(TicketRepository ticketRepository, ReservationRepository reservationRepository, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.reservationRepository = reservationRepository;
        this.flightRepository = flightRepository;
    }

    public Page<TicketDTO> getAllTickets(Pageable pageable) {
        Page<Ticket> ticketPage = ticketRepository.findAll(pageable);
        return ticketPage.map(TicketDTO::ticketToDTO);
    }

    public TicketDTO getTicketById(UUID id) {
        Ticket ticket = verifyTicketExistsById(id);
        return TicketDTO.ticketToDTO(ticket);
    }

    public TicketDTO getTicketByTicketNumberOrReservationId(String ticketNumber, UUID reservationId) {
        if (ticketNumber != null) {
            Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ticket number " + ticketNumber));
            return TicketDTO.ticketToDTO(ticket);
        }

        if (reservationId != null) {
            Ticket ticket = ticketRepository.findByReservationId(reservationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with reservation id " + reservationId));
            return TicketDTO.ticketToDTO(ticket);
        }

        throw new IllegalArgumentException("At least one parameter (ticket number or reservation id) must be provided.");
    }

    @Transactional
    public TicketDTO createTicket(TicketRequestDTO requestDTO) {
        if (ticketRepository.findByTicketNumber(requestDTO.ticketNumber()).isPresent()) {
            throw new DataIntegrityViolationException("Ticket number already exists.");
        }

        Reservation reservation = reservationRepository.findById(requestDTO.reservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + requestDTO.reservationId()));
        Flight flight = flightRepository.findById(requestDTO.flightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + requestDTO.flightId()));

        Ticket ticket = new Ticket(
                UUID.randomUUID(),
                requestDTO.ticketNumber(),
                reservation,
                flight
        );
        Ticket savedTicket = ticketRepository.save(ticket);

        reservation.setTicket(savedTicket);
        reservationRepository.save(reservation);

        return TicketDTO.ticketToDTO(savedTicket);
    }

    @Transactional
    public TicketDTO updateTicket(TicketUpdateDTO updateDTO) {
        Ticket existingTicket = verifyTicketExistsById(updateDTO.id());

        existingTicket.setTicketNumber(updateDTO.ticketNumber());

        Ticket updatedTicket = ticketRepository.save(existingTicket);

        Reservation reservation = updatedTicket.getReservation();
        if (reservation != null) {
            reservation.setTicket(updatedTicket);
            reservationRepository.save(reservation);
        }

        return TicketDTO.ticketToDTO(updatedTicket);
    }

    @Transactional
    public void deleteTicket(UUID id) {
        verifyTicketExistsById(id);
        ticketRepository.deleteById(id);
    }

    private Ticket verifyTicketExistsById(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));
    }
}
