package com.airline_ticket.api.controller;

import com.airline_ticket.api.controller.swagger.AuthControllerInterface;
import com.airline_ticket.api.model.dtos.auth.LoginRequestDTO;
import com.airline_ticket.api.model.dtos.auth.RegisterEmployeeRequestDTO;
import com.airline_ticket.api.model.dtos.auth.RegisterPassengerRequestDTO;
import com.airline_ticket.api.model.dtos.auth.ResponseDTO;
import com.airline_ticket.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerInterface {
    @Autowired
    private AuthService authService;

    @PostMapping("/passengers/login")
    public ResponseEntity<ResponseDTO> loginPassenger(@Valid @RequestBody LoginRequestDTO dto) {
        ResponseDTO response = authService.loginPassenger(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/passengers/register")
    public ResponseEntity<ResponseDTO> registerPassenger(@Valid @RequestBody RegisterPassengerRequestDTO dto) {
        ResponseDTO response = authService.registerPassenger(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/employees/login")
    public ResponseEntity<ResponseDTO> loginEmployee(@Valid @RequestBody LoginRequestDTO dto) {
        ResponseDTO response = authService.loginEmployee(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/employees/register")
    public ResponseEntity<ResponseDTO> registerEmployee(@Valid @RequestBody RegisterEmployeeRequestDTO dto) {
        ResponseDTO response = authService.registerEmployee(dto);
        return ResponseEntity.ok(response);
    }
}
