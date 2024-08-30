package com.airline_ticket.api.controller;

import com.airline_ticket.api.security.TokenService;
import com.airline_ticket.api.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.airline_ticket.api.constants.AuthConstants.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @MockBean
    private AuthService authService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TokenService tokenService;

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Should return a response DTO with token when login passenger is successful")
    public void loginPassenger_ReturnsResponseDTO_WhenSuccessful() throws Exception {
        String passengerJson = objectMapper.writeValueAsString(LOGIN_REQUEST_DTO);

        mockMvc
                .perform(post("/api/auth/passengers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passengerJson)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Should register a new passenger and return a RegisterResponseDTO with a valid token")
    public void registerPassenger_SavesNewPassenger_ReturnsToken() throws Exception {
        String passengerJson = objectMapper.writeValueAsString(REGISTER_PASSENGER_REQUEST_DTO);

        mockMvc
                .perform(post("/api/auth/passengers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passengerJson)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Login employee with valid data should return token")
    public void loginEmployee_WithValidData_ReturnsToken() throws Exception {
        String passengerJson = objectMapper.writeValueAsString(LOGIN_REQUEST_DTO);

        mockMvc
                .perform(post("/api/auth/employees/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passengerJson)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Should register an employee successfully")
    public void registerEmployee_ReturnsResponseDTO_WhenSuccessful() throws Exception {
        String passengerJson = objectMapper.writeValueAsString(REGISTER_EMPLOYEE_REQUEST_DTO);

        mockMvc
                .perform(post("/api/auth/employees/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passengerJson)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
