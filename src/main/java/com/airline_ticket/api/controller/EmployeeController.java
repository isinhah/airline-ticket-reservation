package com.airline_ticket.api.controller;

import com.airline_ticket.api.controller.swagger.EmployeeControllerInterface;
import com.airline_ticket.api.model.dtos.employee.EmployeeDTO;
import com.airline_ticket.api.model.dtos.employee.EmployeeRequestDTO;
import com.airline_ticket.api.model.dtos.employee.EmployeeUpdateDTO;
import com.airline_ticket.api.service.EmployeeService;
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
@RequestMapping("/api/employees")
public class EmployeeController implements EmployeeControllerInterface {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDTO> findAllEmployees(@PageableDefault(size = 10) Pageable pageable) {
        return employeeService.getAllEmployees(pageable).getContent();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO findEmployeeById(@PathVariable UUID id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/byEmail")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO findEmployeeByEmail(@RequestParam String email) {
        return employeeService.getEmployeeByEmail(email);
    }

    @GetMapping("/byName")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDTO> findEmployeesByName(
            @RequestParam String name,
            @PageableDefault(size = 10) Pageable pageable) {
        return employeeService.getEmployeesByName(name, pageable).getContent();
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO) {
        EmployeeDTO employeeDTO = employeeService.createEmployee(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployee(@Valid @RequestBody EmployeeUpdateDTO updateDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(updateDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}