package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Employee;
import com.airline_ticket.api.model.Passenger;
import com.airline_ticket.api.model.dtos.auth.LoginRequestDTO;
import com.airline_ticket.api.model.dtos.auth.RegisterEmployeeRequestDTO;
import com.airline_ticket.api.model.dtos.auth.RegisterPassengerRequestDTO;
import com.airline_ticket.api.model.dtos.auth.ResponseDTO;
import com.airline_ticket.api.repository.EmployeeRepository;
import com.airline_ticket.api.repository.PassengerRepository;
import com.airline_ticket.api.security.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final PassengerRepository passengerRepository;
    private final EmployeeRepository employeeRepository;

    public AuthService(TokenService tokenService, PasswordEncoder passwordEncoder, PassengerRepository passengerRepository, EmployeeRepository employeeRepository) {
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.passengerRepository = passengerRepository;
        this.employeeRepository = employeeRepository;
    }

    public ResponseDTO loginPassenger(LoginRequestDTO dto) {
        Passenger passenger = passengerRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ResourceNotFoundException("Email not found."));

        if (passwordEncoder.matches(dto.password(), passenger.getPassword())) {
            String token = tokenService.generatePassengerToken(passenger);
            Instant expiresAt = tokenService.getExpirationDateFromToken(token);
            return new ResponseDTO(passenger.getName(), token, expiresAt.toString());
        }

        return new ResponseDTO("Invalid credentials", null, null);
    }

    @Transactional
    public ResponseDTO registerPassenger(RegisterPassengerRequestDTO dto) {
        Optional<Passenger> existingPassenger = passengerRepository.findByEmail(dto.email());

        if (existingPassenger.isEmpty()) {
            Passenger newPassenger = new Passenger();
            newPassenger.setPassword(passwordEncoder.encode(dto.password()));
            newPassenger.setEmail(dto.email());
            newPassenger.setName(dto.name());
            newPassenger.setPhone(dto.phone());
            passengerRepository.save(newPassenger);

            String token = tokenService.generatePassengerToken(newPassenger);
            Instant expiresAt = tokenService.getExpirationDateFromToken(token);

            return new ResponseDTO(newPassenger.getName(), token, expiresAt.toString());
        }

        return new ResponseDTO("Email already exists", null, null);
    }

    public ResponseDTO loginEmployee(LoginRequestDTO dto) {
        Employee employee = employeeRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ResourceNotFoundException("Email not found."));

        if (passwordEncoder.matches(dto.password(), employee.getPassword())) {
            String token = tokenService.generateEmployeeToken(employee);
            Instant expiresAt = tokenService.getExpirationDateFromToken(token);
            return new ResponseDTO(employee.getName(), token, expiresAt.toString());
        }

        return new ResponseDTO("Invalid credentials", null, null);
    }

    @Transactional
    public ResponseDTO registerEmployee(RegisterEmployeeRequestDTO dto) {
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(dto.email());
        if (existingEmployee.isEmpty()) {
            Employee newEmployee = new Employee();
            newEmployee.setPassword(passwordEncoder.encode(dto.password()));
            newEmployee.setEmail(dto.email());
            newEmployee.setName(dto.name());
            employeeRepository.save(newEmployee);

            String token = tokenService.generateEmployeeToken(newEmployee);
            Instant expiresAt = tokenService.getExpirationDateFromToken(token);

            return new ResponseDTO(newEmployee.getName(), token, expiresAt.toString());
        }

        return new ResponseDTO("Email already exists", null, null);
    }
}
