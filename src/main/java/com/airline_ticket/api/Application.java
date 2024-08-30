package com.airline_ticket.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Airline Ticket Reservation System",
		description = "REST API for managing airline tickets, reservations, seats, flights, passengers and employees."))
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}