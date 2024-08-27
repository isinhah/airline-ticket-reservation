package com.airline_ticket.api.controller;

import com.airline_ticket.api.model.dtos.ticket.TicketDTO;
import com.airline_ticket.api.model.dtos.ticket.TicketRequestDTO;
import com.airline_ticket.api.model.dtos.ticket.TicketUpdateDTO;
import com.airline_ticket.api.service.TicketService;
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
@RequestMapping("api/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TicketDTO> findAllTickets(@PageableDefault(size = 10) Pageable pageable) {
        return ticketService.getAllTickets(pageable).getContent();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public TicketDTO findTicket(
            @RequestParam(required = false) String ticketNumber,
            @RequestParam(required = false) UUID reservationId) {
        return ticketService.getTicketByTicketNumberOrReservationId(ticketNumber, reservationId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TicketDTO findTicketById(@PathVariable UUID id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@Valid  @RequestBody TicketRequestDTO requestDTO) {
        TicketDTO createdTicket = ticketService.createTicket(requestDTO);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<TicketDTO> updateTicket(@Valid @RequestBody TicketUpdateDTO updateDTO) {
        TicketDTO updatedTicket = ticketService.updateTicket(updateDTO);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
