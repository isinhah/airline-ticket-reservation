package com.airline_ticket.api.controller;

import com.airline_ticket.api.controller.swagger.PassengerControllerInterface;
import com.airline_ticket.api.model.dtos.passenger.PassengerDTO;
import com.airline_ticket.api.model.dtos.passenger.PassengerRequestDTO;
import com.airline_ticket.api.model.dtos.passenger.PassengerUpdateDTO;
import com.airline_ticket.api.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController implements PassengerControllerInterface {

    @Autowired
    private PassengerService passengerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PassengerDTO> findAllPassengers(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return passengerService.getAllPassengers(pageable).getContent();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassengerDTO findPassengerById(@PathVariable UUID id) {
        return passengerService.getPassengerById(id);
    }

    @GetMapping("/searchByName")
    @ResponseStatus(HttpStatus.OK)
    public List<PassengerDTO> findPassengersByName(
            @RequestParam String name,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return passengerService.getPassengersByName(name, pageable).getContent();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public PassengerDTO findPassengerByPhoneOrEmail(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email) {
        return passengerService.getPassengerByPhoneOrEmail(phone, email);
    }

    @PostMapping
    public ResponseEntity<PassengerDTO> createPassenger(@Valid @RequestBody PassengerRequestDTO requestDTO) {
        PassengerDTO createdPassenger = passengerService.createPassenger(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPassenger);
    }

    @PutMapping
    public ResponseEntity<PassengerDTO> updatePassenger(@Valid @RequestBody PassengerUpdateDTO updateDTO
    ) {
        PassengerDTO updatedPassenger = passengerService.updatePassenger(updateDTO);
        return ResponseEntity.ok(updatedPassenger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable UUID id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}