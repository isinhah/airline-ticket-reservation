package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Employee;
import com.airline_ticket.api.model.Passenger;
import com.airline_ticket.api.model.dtos.login_register.ResponseDTO;
import com.airline_ticket.api.repository.EmployeeRepository;
import com.airline_ticket.api.repository.PassengerRepository;
import com.airline_ticket.api.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.airline_ticket.api.constants.AuthConstants.*;
import static com.airline_ticket.api.constants.EmployeeConstants.EMPLOYEE_1;
import static com.airline_ticket.api.constants.PassengerConstants.PASSENGER_1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Should return a response DTO with token when login passenger is successful")
    void loginPassenger_ReturnsResponseDTO_WhenSuccessful() {
        when(passengerRepository.findByEmail(LOGIN_REQUEST_DTO.email())).thenReturn(Optional.of(PASSENGER_1));
        when(passwordEncoder.matches(LOGIN_REQUEST_DTO.password(), PASSENGER_1.getPassword())).thenReturn(true);

        Instant expirationDate = Instant.now().plusSeconds(3600);

        when(tokenService.generatePassengerToken(PASSENGER_1)).thenReturn(TOKEN);
        when(tokenService.getExpirationDateFromToken(TOKEN)).thenReturn(expirationDate);

        ResponseDTO response = authService.loginPassenger(LOGIN_REQUEST_DTO);

        assertNotNull(response);
        assertEquals(PASSENGER_1.getName(), response.name());
        assertEquals(TOKEN, response.token());
        assertEquals(expirationDate.toString(), response.expiresAt());
    }

    @Test
    @DisplayName("Should register a new employee and return a RegisterResponseDTO with a valid token")
    void registerPassenger_SavesNewPassenger_ReturnsToken() {
        Instant expirationDate = Instant.now().plus(1, ChronoUnit.HOURS);

        when(passengerRepository.findByEmail(PASSENGER_1.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(PASSENGER_1.getPassword())).thenReturn(ENCODED_PASSWORD);
        when(tokenService.generatePassengerToken(any(Passenger.class))).thenReturn(TOKEN);
        when(tokenService.getExpirationDateFromToken(TOKEN)).thenReturn(expirationDate);

        ResponseDTO response = authService.registerPassenger(REGISTER_PASSENGER_REQUEST_DTO);

        assertNotNull(response);
        assertEquals(PASSENGER_1.getName(), response.name());
        assertEquals(TOKEN, response.token());
        assertEquals(expirationDate.toString(), response.expiresAt());

        verify(passengerRepository, times(1)).save(any(Passenger.class));
    }

    @Test
    @DisplayName("Should return invalid credentials message when login passenger with wrong password")
    void loginPassenger_ReturnsInvalidCredentials_WhenPasswordIsIncorrect() {
        when(passengerRepository.findByEmail(LOGIN_REQUEST_DTO.email())).thenReturn(Optional.of(PASSENGER_1));
        when(passwordEncoder.matches(LOGIN_REQUEST_DTO.password(), PASSENGER_1.getPassword())).thenReturn(false);

        ResponseDTO response = authService.loginPassenger(LOGIN_REQUEST_DTO);

        assertNotNull(response);
        assertEquals("Invalid credentials", response.name());
        assertNull(response.token());
        assertNull(response.expiresAt());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when login passenger with non-existing email")
    void loginPassenger_ThrowsResourceNotFoundException_WhenEmailDoesNotExist() {
        when(passengerRepository.findByEmail(LOGIN_REQUEST_DTO.email())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            authService.loginPassenger(LOGIN_REQUEST_DTO);
        });
    }

    @Test
    @DisplayName("Should return a response DTO with token when login employee is successful")
    void loginEmployee_ReturnsResponseDTO_WhenSuccessful() {
        when(employeeRepository.findByEmail(LOGIN_REQUEST_DTO.email())).thenReturn(Optional.of(EMPLOYEE_1));
        when(passwordEncoder.matches(LOGIN_REQUEST_DTO.password(), EMPLOYEE_1.getPassword())).thenReturn(true);

        Instant expirationDate = Instant.now().plusSeconds(3600);

        when(tokenService.generateEmployeeToken(EMPLOYEE_1)).thenReturn(TOKEN);
        when(tokenService.getExpirationDateFromToken(TOKEN)).thenReturn(expirationDate);

        ResponseDTO response = authService.loginEmployee(LOGIN_REQUEST_DTO);

        assertNotNull(response);
        assertEquals(EMPLOYEE_1.getName(), response.name());
        assertEquals(TOKEN, response.token());
        assertEquals(expirationDate.toString(), response.expiresAt());
    }

    @Test
    @DisplayName("Should register an employee successfully")
    void registerEmployee_ReturnsResponseDTO_WhenSuccessful() {
        Instant expirationDate = Instant.now().plus(1, ChronoUnit.HOURS);

        when(employeeRepository.findByEmail(EMPLOYEE_1.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(EMPLOYEE_1.getPassword())).thenReturn(ENCODED_PASSWORD);
        when(tokenService.generateEmployeeToken(any(Employee.class))).thenReturn(TOKEN);
        when(tokenService.getExpirationDateFromToken(TOKEN)).thenReturn(expirationDate);

        ResponseDTO response = authService.registerEmployee(REGISTER_EMPLOYEE_REQUEST_DTO);

        assertNotNull(response);
        assertEquals(EMPLOYEE_1.getName(), response.name());
        assertEquals(TOKEN, response.token());
        assertEquals(expirationDate.toString(), response.expiresAt());

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should return invalid credentials message when login employee with wrong password")
    void loginEmployee_ReturnsInvalidCredentials_WhenPasswordIsIncorrect() {
        when(employeeRepository.findByEmail(LOGIN_REQUEST_DTO.email())).thenReturn(Optional.of(EMPLOYEE_1));
        when(passwordEncoder.matches(LOGIN_REQUEST_DTO.password(), EMPLOYEE_1.getPassword())).thenReturn(false);

        ResponseDTO response = authService.loginEmployee(LOGIN_REQUEST_DTO);

        assertNotNull(response);
        assertEquals("Invalid credentials", response.name());
        assertNull(response.token());
        assertNull(response.expiresAt());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when login employee with non-existing email")
    void loginEmployee_ThrowsResourceNotFoundException_WhenEmailDoesNotExist() {
        when(employeeRepository.findByEmail(LOGIN_REQUEST_DTO.email())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            authService.loginEmployee(LOGIN_REQUEST_DTO);
        });
    }
}