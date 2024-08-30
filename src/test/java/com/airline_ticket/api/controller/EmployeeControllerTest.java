package com.airline_ticket.api.controller;

import com.airline_ticket.api.model.dtos.employee.EmployeeDTO;
import com.airline_ticket.api.security.TokenService;
import com.airline_ticket.api.service.EmployeeService;
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

import static com.airline_ticket.api.constants.EmployeeConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TokenService tokenService;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findAllEmployees_ShouldReturnEmployeeList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<EmployeeDTO> page = new PageImpl<>(EMPLOYEES, pageable, EMPLOYEES.size());

        when(employeeService.getAllEmployees(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(EMPLOYEE_DTO_1.id().toString()))
                .andExpect(jsonPath("$[0].name").value(EMPLOYEE_DTO_1.name()))
                .andExpect(jsonPath("$[0].email").value(EMPLOYEE_DTO_1.email()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findEmployeeById_ShouldReturnEmployee() throws Exception {
        when(employeeService.getEmployeeById(EMPLOYEE_1.getId())).thenReturn(EMPLOYEE_DTO_1);

        mockMvc.perform(get("/api/employees/{id}", EMPLOYEE_1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EMPLOYEE_DTO_1.id().toString()))
                .andExpect(jsonPath("$.name").value(EMPLOYEE_DTO_1.name()))
                .andExpect(jsonPath("$.email").value(EMPLOYEE_DTO_1.email()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findEmployeeByEmail_ShouldReturnEmployee() throws Exception {
        when(employeeService.getEmployeeByEmail(EMPLOYEE_1.getEmail())).thenReturn(EMPLOYEE_DTO_1);

        mockMvc.perform(get("/api/employees/byEmail")
                        .param("email", EMPLOYEE_1.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EMPLOYEE_DTO_1.id().toString()))
                .andExpect(jsonPath("$.name").value(EMPLOYEE_DTO_1.name()))
                .andExpect(jsonPath("$.email").value(EMPLOYEE_DTO_1.email()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findEmployeesByName_ShouldReturnEmployeeList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<EmployeeDTO> page = new PageImpl<>(List.of(EMPLOYEE_DTO_1), pageable, 1);

        when(employeeService.getEmployeesByName(EMPLOYEE_DTO_1.name(), pageable)).thenReturn(page);

        mockMvc.perform(get("/api/employees/byName")
                        .param("name", EMPLOYEE_DTO_1.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(EMPLOYEE_DTO_1.id().toString()))
                .andExpect(jsonPath("$[0].name").value(EMPLOYEE_DTO_1.name()))
                .andExpect(jsonPath("$[0].email").value(EMPLOYEE_DTO_1.email()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should create a new employee and return Created")
    public void createEmployee_WithValidData_ReturnsEmployeeCreated() throws Exception {
        String employeeJson = objectMapper.writeValueAsString(EMPLOYEE_REQUEST_DTO);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should replace an existing employee and returns Ok")
    public void replaceEmployee_WithValidData_ReturnsOk() throws Exception {
        String employeeJson = objectMapper.writeValueAsString(EMPLOYEE_UPDATE_DTO);

        mockMvc
                .perform(put("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should delete a employee by id and returns No Content")
    public void deleteEmployeeById_ReturnsNoContent() throws Exception {
        when(employeeService.getEmployeeById(EMPLOYEE_1.getId())).thenReturn(EMPLOYEE_DTO_1);

        mockMvc
                .perform(delete("/api/employees/{id}", EMPLOYEE_1.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
