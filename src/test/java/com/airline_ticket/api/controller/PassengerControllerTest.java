package com.airline_ticket.api.controller;

import com.airline_ticket.api.model.dtos.passenger.PassengerDTO;
import com.airline_ticket.api.security.TokenService;
import com.airline_ticket.api.service.PassengerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static com.airline_ticket.api.constants.PassengerConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PassengerController.class)
public class PassengerControllerTest {
    @MockBean
    private PassengerService passengerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TokenService tokenService;

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findAllPassengers_ShouldReturnPassengerList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PassengerDTO> page = new PageImpl<>(PASSENGERS, pageable, PASSENGERS.size());

        when(passengerService.getAllPassengers(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/passengers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(PASSENGER_DTO_1.id().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findPassengerById_ShouldReturnPassenger() throws Exception {
        when(passengerService.getPassengerById(PASSENGER_1.getId())).thenReturn(PASSENGER_DTO_1);

        mockMvc.perform(get("/api/passengers/{id}", PASSENGER_1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PASSENGER_DTO_1.id().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findPassengersByName_ShouldReturnPassengerList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PassengerDTO> page = new PageImpl<>(List.of(PASSENGER_DTO_1), pageable, 1);

        when(passengerService.getPassengersByName(PASSENGER_DTO_1.name(), pageable)).thenReturn(page);

        mockMvc.perform(get("/api/passengers/searchByName")
                        .param("name", PASSENGER_DTO_1.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(PASSENGER_DTO_1.id().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void findPassengerByPhoneOrEmail_ShouldReturnPassenger() throws Exception {
        when(passengerService.getPassengerByPhoneOrEmail(PASSENGER_DTO_1.phone(), PASSENGER_DTO_1.email()))
                .thenReturn(PASSENGER_DTO_1);

        mockMvc.perform(get("/api/passengers/search")
                        .param("phone", PASSENGER_DTO_1.phone())
                        .param("email", PASSENGER_DTO_1.email())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PASSENGER_DTO_1.id().toString()))
                .andExpect(jsonPath("$.name").value(PASSENGER_DTO_1.name()))
                .andExpect(jsonPath("$.email").value(PASSENGER_DTO_1.email()))
                .andExpect(jsonPath("$.phone").value(PASSENGER_DTO_1.phone()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Should create a new passenger and return Created")
    public void createPassenger_WithValidData_ReturnsPassengerCreated() throws Exception {
        String passengerJson = objectMapper.writeValueAsString(PASSENGER_REQUEST_DTO);

        mockMvc.perform(post("/api/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passengerJson)
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Should replace an existing passenger and returns Ok")
    public void replacePassenger_WithValidData_ReturnsOk() throws Exception {
        String passengerJson = objectMapper.writeValueAsString(PASSENGER_UPDATE_DTO);

        mockMvc
                .perform(put("/api/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passengerJson)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Should delete a passenger by id and returns No Content")
    public void deletePassengerById_ReturnsNoContent() throws Exception {
        when(passengerService.getPassengerById(PASSENGER_1.getId())).thenReturn(PASSENGER_DTO_1);

        mockMvc
                .perform(delete("/api/passengers/{id}", PASSENGER_1.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
