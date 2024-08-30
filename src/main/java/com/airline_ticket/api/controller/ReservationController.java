package com.airline_ticket.api.controller;

import com.airline_ticket.api.controller.swagger.ReservationControllerInterface;
import com.airline_ticket.api.model.dtos.reservation.ReservationDTO;
import com.airline_ticket.api.model.dtos.reservation.ReservationRequestDTO;
import com.airline_ticket.api.model.dtos.reservation.ReservationUpdateDTO;
import com.airline_ticket.api.service.ReservationService;
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
@RequestMapping("/api/reservations")
public class ReservationController implements ReservationControllerInterface {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> findAllReservations(@PageableDefault(size = 10) Pageable pageable) {
        return reservationService.getAllReservations(pageable).getContent();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO findReservationById(@PathVariable UUID id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/searchByDate")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> findReservationsByDate(@RequestParam String reservationDate,
                                                       @PageableDefault(size = 10) Pageable pageable) {
        return reservationService.getReservationsByDate(reservationDate, pageable).getContent();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> findReservations(
            @RequestParam(required = false) UUID seatId,
            @RequestParam(required = false) UUID passengerId,
            @PageableDefault(size = 10) Pageable pageable) {
        return reservationService.getReservations(seatId, passengerId, pageable).getContent();
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationRequestDTO requestDTO) {
        ReservationDTO reservationDTO = reservationService.createReservation(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
    }

    @PutMapping
    public ResponseEntity<ReservationDTO> updateReservation(@Valid @RequestBody ReservationUpdateDTO updateDTO) {
        ReservationDTO reservationDTO = reservationService.updateReservation(updateDTO);
        return ResponseEntity.ok(reservationDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
