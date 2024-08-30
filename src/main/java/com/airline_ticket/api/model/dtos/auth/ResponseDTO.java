package com.airline_ticket.api.model.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Response DTO", description = "DTO for the response containing name, token, and expiration time")
public record ResponseDTO(

        @Schema(description = "Name of the user associated with the response.", example = "Isabel")
        String name,

        @Schema(description = "Token provided in the response.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,

        @Schema(description = "Expiration time of the token.", example = "2024-08-30T15:30:00Z")
        String expiresAt
) {}