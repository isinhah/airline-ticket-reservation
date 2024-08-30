package com.airline_ticket.api.constants;

import com.airline_ticket.api.model.dtos.login_register.LoginRequestDTO;
import com.airline_ticket.api.model.dtos.login_register.RegisterEmployeeRequestDTO;
import com.airline_ticket.api.model.dtos.login_register.RegisterPassengerRequestDTO;

import static com.airline_ticket.api.constants.EmployeeConstants.EMPLOYEE_1;
import static com.airline_ticket.api.constants.PassengerConstants.PASSENGER_1;

public class AuthConstants {
    public static final String ENCODED_PASSWORD = "senhacodificada";
    public static final String TOKEN = "token";

    public static final LoginRequestDTO LOGIN_REQUEST_DTO = new LoginRequestDTO(
            PASSENGER_1.getEmail(),
            PASSENGER_1.getPassword()
    );

    public static final RegisterEmployeeRequestDTO REGISTER_EMPLOYEE_REQUEST_DTO = new RegisterEmployeeRequestDTO(
            EMPLOYEE_1.getName(),
            EMPLOYEE_1.getEmail(),
            EMPLOYEE_1.getPassword()
    );

    public static final RegisterPassengerRequestDTO REGISTER_PASSENGER_REQUEST_DTO = new RegisterPassengerRequestDTO(
            PASSENGER_1.getName(),
            PASSENGER_1.getEmail(),
            PASSENGER_1.getPassword(),
            PASSENGER_1.getPhone()
    );
}
