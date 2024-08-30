package com.airline_ticket.api.controller;

import com.airline_ticket.api.model.dtos.flight.FlightDTO;
import com.airline_ticket.api.security.TokenService;
import com.airline_ticket.api.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.airline_ticket.api.constants.FlightConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {
    @MockBean
    private FlightService flightService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findAllFlights_ShouldReturnFlightList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FlightDTO> page = new PageImpl<>(FLIGHTS, pageable, FLIGHTS.size());

        when(flightService.getAllFlights(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(FLIGHT_DTO_1.id().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findFlightById_ShouldReturnFlight() throws Exception {
        when(flightService.getFlightById(FLIGHT_1.getId())).thenReturn(FLIGHT_DTO_1);

        mockMvc.perform(get("/api/flights/{id}", FLIGHT_1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FLIGHT_DTO_1.id().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findFlightByFlightNumber_ShouldReturnFlight() throws Exception {
        when(flightService.getFlightByFlightNumber(FLIGHT_1.getFlightNumber())).thenReturn(FLIGHT_DTO_1);

        mockMvc.perform(get("/api/flights/searchByFlightNumber")
                        .param("flightNumber", FLIGHT_1.getFlightNumber())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FLIGHT_DTO_1.id().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findFlightsByAirline_ShouldReturnFlightList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FlightDTO> page = new PageImpl<>(List.of(FLIGHT_DTO_1), pageable, 1);

        when(flightService.getFlightsByAirline(FLIGHT_DTO_1.airline(), pageable)).thenReturn(page);

        mockMvc.perform(get("/api/flights/searchByAirline")
                        .param("airline", FLIGHT_DTO_1.airline())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(FLIGHT_DTO_1.id().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findFlightByLocation_ShouldReturnFlight() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FlightDTO> page = new PageImpl<>(List.of(FLIGHT_DTO_1), pageable, 1);

        when(flightService.getFlightsByLocation(FLIGHT_DTO_1.origin(), FLIGHT_DTO_1.destination(), pageable)).thenReturn(page);

        mockMvc.perform(get("/api/flights/searchByLocation")
                        .param("origin", FLIGHT_DTO_1.origin())
                        .param("destination", FLIGHT_DTO_1.destination())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findFlightsByPrice_ShouldReturnFlightList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FlightDTO> page = new PageImpl<>(List.of(FLIGHT_DTO_1), pageable, 1);

        when(flightService.getFlightsByPrice(FLIGHT_DTO_1.price(), pageable)).thenReturn(page);

        mockMvc.perform(get("/api/flights/searchByPrice")
                        .param("price", String.valueOf(FLIGHT_DTO_1.price()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(FLIGHT_DTO_1.id().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findFlightsByDepartureTime_ShouldReturnFlightList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FlightDTO> page = new PageImpl<>(List.of(FLIGHT_DTO_1), pageable, 1);

        when(flightService.getFlightsByDepartureTime(FLIGHT_DTO_1.departureTime(), FLIGHT_DTO_1.departureTime(), pageable)).thenReturn(page);

        mockMvc.perform(get("/api/flights/searchByDepartureTime")
                        .param("start", FLIGHT_DTO_1.departureTime().toString())
                        .param("end", FLIGHT_DTO_1.departureTime().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(FLIGHT_DTO_1.id().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findFlightsByArrivalTime_ShouldReturnFlightList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FlightDTO> page = new PageImpl<>(List.of(FLIGHT_DTO_1), pageable, 1);

        when(flightService.getFlightsByArrivalTime(FLIGHT_DTO_1.arrivalTime(), FLIGHT_DTO_1.arrivalTime(), pageable)).thenReturn(page);

        mockMvc.perform(get("/api/flights/searchByArrivalTime")
                        .param("start", FLIGHT_DTO_1.arrivalTime().toString())
                        .param("end", FLIGHT_DTO_1.arrivalTime().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(FLIGHT_DTO_1.id().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should create a new flight and return Created")
    public void createFlight_WithValidData_ReturnsFlightCreated() throws Exception {
        String flightJson = objectMapper.writeValueAsString(FLIGHT_REQUEST_DTO);

        mockMvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(flightJson)
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should replace an existing flight and returns Ok")
    public void replaceFlight_WithValidData_ReturnsOk() throws Exception {
        String flightJson = objectMapper.writeValueAsString(FLIGHT_UPDATE_DTO);

        mockMvc
                .perform(put("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(flightJson)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should delete a flight by id and returns No Content")
    public void deleteFlightById_ReturnsNoContent() throws Exception {
        when(flightService.getFlightById(FLIGHT_1.getId())).thenReturn(FLIGHT_DTO_1);

        mockMvc
                .perform(delete("/api/flights/{id}", FLIGHT_DTO_1.id())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}