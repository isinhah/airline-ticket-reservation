package com.airline_ticket.api.constants;

import com.airline_ticket.api.model.Passenger;
import com.airline_ticket.api.model.dtos.passenger.PassengerDTO;
import com.airline_ticket.api.model.dtos.passenger.PassengerRequestDTO;
import com.airline_ticket.api.model.dtos.passenger.PassengerUpdateDTO;

import java.util.List;
import java.util.UUID;

public class PassengerConstants {
    public static final Passenger PASSENGER_1 = new Passenger(UUID.randomUUID(), "isabel", "isabel@example.com", "1234567890", "91234-5678");
    public static final Passenger PASSENGER_2 = new Passenger(UUID.randomUUID(), "isabela", "isabela@example.com", "1234567890", "91234-5678");

    public static final PassengerDTO PASSENGER_DTO_1 = new PassengerDTO(PASSENGER_1.getId(), PASSENGER_1.getName(), PASSENGER_1.getEmail(), PASSENGER_1.getPassword(), PASSENGER_1.getPhone());
    public static final PassengerDTO PASSENGER_DTO_2 = new PassengerDTO(PASSENGER_2.getId(), PASSENGER_2.getName(), PASSENGER_2.getEmail(), PASSENGER_2.getPassword(), PASSENGER_2.getPhone());

    public static final PassengerRequestDTO PASSENGER_REQUEST_DTO = new PassengerRequestDTO(PASSENGER_1.getName(), PASSENGER_1.getEmail(), PASSENGER_1.getPassword(), PASSENGER_1.getPhone());
    public static final PassengerUpdateDTO PASSENGER_UPDATE_DTO = new PassengerUpdateDTO(PASSENGER_2.getId(), PASSENGER_2.getName(), PASSENGER_2.getEmail(), PASSENGER_2.getPassword(), PASSENGER_2.getPhone());

    public static final List<PassengerDTO> PASSENGERS = List.of(PASSENGER_DTO_1, PASSENGER_DTO_2);
}
