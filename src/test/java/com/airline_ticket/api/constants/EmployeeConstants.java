package com.airline_ticket.api.constants;

import com.airline_ticket.api.model.Employee;
import com.airline_ticket.api.model.dtos.employee.EmployeeDTO;
import com.airline_ticket.api.model.dtos.employee.EmployeeRequestDTO;
import com.airline_ticket.api.model.dtos.employee.EmployeeUpdateDTO;

import java.util.List;
import java.util.UUID;

public class EmployeeConstants {
    public static final Employee EMPLOYEE_1 = new Employee(UUID.randomUUID(), "isabel", "isabel@example.com", "1234567890");
    public static final Employee EMPLOYEE_2 = new Employee(UUID.randomUUID(), "isabela", "isabela@example.com", "1234567890");

    public static final EmployeeDTO EMPLOYEE_DTO_1 = new EmployeeDTO(EMPLOYEE_1.getId(), EMPLOYEE_1.getName(), EMPLOYEE_1.getEmail(), EMPLOYEE_1.getPassword());
    public static final EmployeeDTO EMPLOYEE_DTO_2 = new EmployeeDTO(EMPLOYEE_2.getId(), EMPLOYEE_2.getName(), EMPLOYEE_2.getEmail(), EMPLOYEE_2.getPassword());

    public static final EmployeeRequestDTO EMPLOYEE_REQUEST_DTO = new EmployeeRequestDTO(EMPLOYEE_1.getName(), EMPLOYEE_1.getEmail(), EMPLOYEE_1.getPassword());
    public static final EmployeeUpdateDTO EMPLOYEE_UPDATE_DTO = new EmployeeUpdateDTO(EMPLOYEE_2.getId(), EMPLOYEE_2.getName(), EMPLOYEE_2.getEmail(), EMPLOYEE_2.getPassword());

    public static final List<EmployeeDTO> EMPLOYEES = List.of(EMPLOYEE_DTO_1, EMPLOYEE_DTO_2);
}
