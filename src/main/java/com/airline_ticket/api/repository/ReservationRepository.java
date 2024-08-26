package com.airline_ticket.api.repository;

import com.airline_ticket.api.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    Page<Reservation> findByReservationDate(ZonedDateTime reservationDate, Pageable pageable);
    Page<Reservation> findBySeatIdAndPassengerId(UUID seatId, UUID passengerId, Pageable pageable);
    Page<Reservation> findBySeatId(UUID seatId, Pageable pageable);
    Page<Reservation> findByPassengerId(UUID passengerId, Pageable pageable);
}