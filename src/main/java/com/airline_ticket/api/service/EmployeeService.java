package com.airline_ticket.api.service;

import com.airline_ticket.api.exceptions.ResourceNotFoundException;
import com.airline_ticket.api.model.Employee;
import com.airline_ticket.api.model.dtos.employee.EmployeeDTO;
import com.airline_ticket.api.model.dtos.employee.EmployeeRequestDTO;
import com.airline_ticket.api.model.dtos.employee.EmployeeUpdateDTO;
import com.airline_ticket.api.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(EmployeeDTO::employeeToDTO);
    }

    public EmployeeDTO getEmployeeById(UUID id) {
        Employee employee = verifyEmployeeExistsById(id);
        return EmployeeDTO.employeeToDTO(employee);
    }

    public EmployeeDTO getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email " + email));
        return EmployeeDTO.employeeToDTO(employee);
    }

    public Page<EmployeeDTO> getEmployeesByName(String name, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findByNameContaining(name, pageable);
        return employeePage.map(EmployeeDTO::employeeToDTO);
    }

    @Transactional
    public EmployeeDTO createEmployee(EmployeeRequestDTO requestDTO) {
        if (employeeRepository.findByEmail(requestDTO.email()).isPresent()) {
            throw new DataIntegrityViolationException("Email already exists.");
        }

        Employee employee = new Employee(
                UUID.randomUUID(),
                requestDTO.name(),
                requestDTO.email(),
                requestDTO.password()
        );
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeDTO.employeeToDTO(savedEmployee);
    }

    @Transactional
    public EmployeeDTO updateEmployee(EmployeeUpdateDTO updateDTO) {
        Employee existingEmployee = verifyEmployeeExistsById(updateDTO.id());

        existingEmployee.setName(updateDTO.name());
        existingEmployee.setEmail(updateDTO.email());
        existingEmployee.setPassword(updateDTO.password());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeDTO.employeeToDTO(updatedEmployee);
    }

    @Transactional
    public void deleteEmployee(UUID id) {
        verifyEmployeeExistsById(id);
        employeeRepository.deleteById(id);
    }

    private Employee verifyEmployeeExistsById(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }
}
