package com.airline_ticket.api.repository;

import com.airline_ticket.api.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
    Page<Passenger> findAll(Pageable pageable);
    Optional<Passenger> findByPhone(String phone);
    Optional<Passenger> findByEmail(String email);
    Page<Passenger> findByNameContaining(String name, Pageable pageable);
}