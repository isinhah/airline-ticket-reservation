package com.airline_ticket.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        .requestMatchers("/api/auth/passengers/login", "/api/auth/passengers/register").permitAll()
                        .requestMatchers("/api/auth/employees/login", "/api/auth/employees/register").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/flights/**", "/api/seats/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/passengers/{id}", "/api/reservations/{id}", "/api/tickets/{id}").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/passengers", "/api/reservations").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/passengers", "/api/reservations").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/passengers/{id}", "/api/reservations/{id}", "/api/tickets/{id}").hasAnyAuthority("USER", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/employees/**",
                                "/api/reservations", "/api/reservations/search", "/api/reservations/searchByDate",
                                "/api/tickets", "/api/tickets/search",
                                "/api/passengers", "/api/passengers/searchByName", "/api/passengers/search").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/employees", "/api/tickets", "/api/seats", "/api/flights").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/employees", "/api/tickets", "/api/seats", "/api/flights").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/{id}", "/api/seats/{id}", "/api/flights/{id}").hasAuthority("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
