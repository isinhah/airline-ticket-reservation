package com.airline_ticket.api.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "reservation_date", nullable = false)
    private ZonedDateTime reservationDate;

    @OneToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;
    @OneToOne(mappedBy = "reservation")
    private Ticket ticket;

    public Reservation() {}

    public Reservation(UUID id, ZonedDateTime reservationDate, Seat seat, Passenger passenger) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.seat = seat;
        this.passenger = passenger;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ZonedDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(ZonedDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Reservation that = (Reservation) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}