package com.airline_ticket.api.repository;

import com.airline_ticket.api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    Optional<Ticket> findByReservationId(UUID reservationId);
}