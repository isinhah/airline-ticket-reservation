package com.airline_ticket.api.repository;

import com.airline_ticket.api.model.Employee;
import com.airline_ticket.api.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Page<Employee> findAll(Pageable pageable);
    Optional<Employee> findByEmail(String email);
    Page<Employee> findByNameContaining(String name, Pageable pageable);
}