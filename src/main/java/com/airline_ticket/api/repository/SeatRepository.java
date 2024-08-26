package com.airline_ticket.api.repository;

import com.airline_ticket.api.model.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    Page<Seat> findByIsAvailable(Boolean isAvailable, Pageable pageable);
    Page<Seat> findBySeatNumberAndFlightId(String seatNumber, UUID flightId, Pageable pageable);
    Page<Seat> findBySeatNumber(String seatNumber, Pageable pageable);
    Page<Seat> findByFlightId(UUID id, Pageable pageable);
}