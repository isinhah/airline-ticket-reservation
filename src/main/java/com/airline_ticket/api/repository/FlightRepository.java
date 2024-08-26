package com.airline_ticket.api.repository;

import com.airline_ticket.api.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    Page<Flight> findByAirlineContainingIgnoreCase(String airline, Pageable pageable);

    Page<Flight> findByOriginAndDestination(String origin, String destination, Pageable pageable);

    Page<Flight> findByOriginContainingIgnoreCase(String origin, Pageable pageable);

    Page<Flight> findByDestinationContainingIgnoreCase(String destination, Pageable pageable);

    Page<Flight> findByDepartureTimeBetween(ZonedDateTime start, ZonedDateTime end, Pageable pageable);

    Page<Flight> findByArrivalTimeBetween(ZonedDateTime start, ZonedDateTime end, Pageable pageable);

    Page<Flight> findByPrice(Double price, Pageable pageable);
}