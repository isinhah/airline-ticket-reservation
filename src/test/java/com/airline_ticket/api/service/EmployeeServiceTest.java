package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Employee;
import com.airline_ticket.api.model.dtos.employee.EmployeeDTO;
import com.airline_ticket.api.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static com.airline_ticket.api.constants.EmployeeConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a list of employees inside page object when successful")
    void getAllEmployees_ReturnsEmployees_WhenSuccessful() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Employee> page = new PageImpl<>(Arrays.asList(EMPLOYEE_1, EMPLOYEE_2), pageable, 2);

        when(employeeRepository.findAll(pageable)).thenReturn(page);

        Page<EmployeeDTO> result = employeeService.getAllEmployees(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().contains(EMPLOYEE_DTO_1));
        assertTrue(result.getContent().contains(EMPLOYEE_DTO_2));
    }

    @Test
    @DisplayName("Should return an empty list of employees inside page object when there are no employees")
    void getAllEmployees_ReturnsEmployees_WhenThereAreNoEmployees() {
        Page<Employee> emptyEmployeePage = new PageImpl<>(Collections.emptyList());
        Pageable pageable = mock(Pageable.class);

        when(employeeRepository.findAll(pageable)).thenReturn(emptyEmployeePage);

        Page<EmployeeDTO> result = employeeService.getAllEmployees(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return an employee by id when successful")
    void getEmployeeById_ReturnsEmployee_WhenSuccessful() {
        when(employeeRepository.findById(EMPLOYEE_1.getId())).thenReturn(Optional.of(EMPLOYEE_1));

        EmployeeDTO result = employeeService.getEmployeeById(EMPLOYEE_1.getId());

        assertNotNull(result);
        assertEquals(EMPLOYEE_DTO_1, result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when getting an employee by non-existing id")
    void getEmployeeById_ThrowsResourceNotFoundException_WhenEmployeeNotFound() {
        when(employeeRepository.findById(EMPLOYEE_1.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(EMPLOYEE_1.getId());
        });

        assertEquals("Employee not found with id " + EMPLOYEE_1.getId(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should return an employee by email when successful")
    void getEmployeeByEmail_ReturnsEmployee_WhenSuccessful() {
        when(employeeRepository.findByEmail(EMPLOYEE_1.getEmail())).thenReturn(Optional.of(EMPLOYEE_1));

        EmployeeDTO result = employeeService.getEmployeeByEmail(EMPLOYEE_1.getEmail());

        assertNotNull(result);
        assertEquals(EMPLOYEE_DTO_1, result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when getting an employee by non-existing email")
    void getEmployeeByEmail_ThrowsResourceNotFoundException_WhenEmployeeNotFound() {
        when(employeeRepository.findByEmail(EMPLOYEE_1.getEmail())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeByEmail(EMPLOYEE_1.getEmail());
        });

        assertEquals("Employee not found with email " + EMPLOYEE_1.getEmail(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should return a list of employees by name inside page object when successful")
    void getEmployeesByName_ReturnsEmployees_WhenSuccessful() {
        String name = "isabel";
        Pageable pageable = PageRequest.of(0, 10);

        Page<Employee> employeePage = new PageImpl<>(Arrays.asList(EMPLOYEE_1, EMPLOYEE_2), pageable, 2);
        Page<EmployeeDTO> expectedPageDTO = new PageImpl<>(Arrays.asList(EMPLOYEE_DTO_1, EMPLOYEE_DTO_2), pageable, 2);

        when(employeeRepository.findByNameContaining(name, pageable)).thenReturn(employeePage);

        Page<EmployeeDTO> result = employeeService.getEmployeesByName(name, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(expectedPageDTO, result);
    }

    @Test
    @DisplayName("Should return an empty page when no employees are found by name")
    void getEmployeesByName_ReturnsEmptyPage_WhenNoEmployeesFound() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(employeeRepository.findByNameContaining(EMPLOYEE_1.getName(), pageable)).thenReturn(emptyPage);

        Page<EmployeeDTO> result = employeeService.getEmployeesByName(EMPLOYEE_1.getName(), pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should create an employee when successful")
    void createEmployee() {
        when(employeeRepository.findByEmail(EMPLOYEE_1.getEmail())).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(EMPLOYEE_1);

        EmployeeDTO result = employeeService.createEmployee(EMPLOYEE_REQUEST_DTO);

        assertNotNull(result);
        assertEquals(EMPLOYEE_DTO_1.id(), result.id());
        assertEquals(EMPLOYEE_DTO_1.name(), result.name());
        assertEquals(EMPLOYEE_DTO_1.email(), result.email());
        assertEquals(EMPLOYEE_DTO_1.password(), result.password());
    }

    @Test
    @DisplayName("Should throw DataIntegrityViolationException when creating an employee with an existing email")
    void createEmployee_ThrowsDataIntegrityViolationException_WhenEmailExists() {
        when(employeeRepository.findByEmail(EMPLOYEE_REQUEST_DTO.email())).thenReturn(Optional.of(new Employee()));

        DataIntegrityViolationException thrown = assertThrows(DataIntegrityViolationException.class, () -> {
            employeeService.createEmployee(EMPLOYEE_REQUEST_DTO);
        });

        assertEquals("Email already exists.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should update an existing employee")
    void updateEmployee() {
        when(employeeRepository.findById(EMPLOYEE_2.getId())).thenReturn(Optional.of(EMPLOYEE_2));
        when(employeeRepository.save(any(Employee.class))).thenReturn(EMPLOYEE_2);

        EmployeeDTO result = employeeService.updateEmployee(EMPLOYEE_UPDATE_DTO);

        assertNotNull(result);
        assertEquals(EMPLOYEE_DTO_2, result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating a non-existing employee")
    void updateEmployee_ThrowsResourceNotFoundException_WhenEmployeeNotFound() {
        when(employeeRepository.findById(EMPLOYEE_2.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(EMPLOYEE_UPDATE_DTO);
        });

        assertEquals("Employee not found with id " + EMPLOYEE_2.getId(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should delete an employee when successful")
    void deleteEmployee() {
        when(employeeRepository.findById(EMPLOYEE_1.getId())).thenReturn(Optional.of(EMPLOYEE_1));

        employeeService.deleteEmployee(EMPLOYEE_1.getId());

        verify(employeeRepository).deleteById(EMPLOYEE_1.getId());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting a non-existing employee")
    void deleteEmployee_ThrowsResourceNotFoundException_WhenEmployeeNotFound() {
        when(employeeRepository.findById(EMPLOYEE_1.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(EMPLOYEE_1.getId()));
    }
}