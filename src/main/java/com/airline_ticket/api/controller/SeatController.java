package com.airline_ticket.api.controller;

import com.airline_ticket.api.model.dtos.seat.SeatDTO;
import com.airline_ticket.api.model.dtos.seat.SeatRequestDTO;
import com.airline_ticket.api.model.dtos.seat.SeatUpdateDTO;
import com.airline_ticket.api.service.SeatService;
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
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SeatDTO> findAllSeats(@PageableDefault(size = 10) Pageable pageable) {
        return seatService.getAllSeats(pageable).getContent();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SeatDTO findSeatById(@PathVariable UUID id) {
        return seatService.getSeatById(id);
    }

    @GetMapping("/searchByAvailability")
    @ResponseStatus(HttpStatus.OK)
    public List<SeatDTO> findAvailableSeats(@RequestParam Boolean isAvailable, @PageableDefault(size = 10) Pageable pageable) {
        return seatService.getAvailableSeats(isAvailable, pageable).getContent();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<SeatDTO> findSeats(
            @RequestParam(required = false) String seatNumber,
            @RequestParam(required = false) UUID flightId,
            @PageableDefault(size = 10) Pageable pageable) {
        return seatService.getSeats(seatNumber, flightId, pageable).getContent();
    }

    @PostMapping
    public ResponseEntity<SeatDTO> createSeat(@Valid  @RequestBody SeatRequestDTO requestDTO) {
        SeatDTO createdSeat = seatService.createSeat(requestDTO);
        return new ResponseEntity<>(createdSeat, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SeatDTO> updateSeat(@Valid @RequestBody SeatUpdateDTO updateDTO) {
        SeatDTO updatedSeat = seatService.updateSeat(updateDTO);
        return ResponseEntity.ok(updatedSeat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable UUID id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}
