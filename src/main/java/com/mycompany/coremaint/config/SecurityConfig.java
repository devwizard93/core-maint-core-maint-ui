package com.mycompany.coremaint.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.coremaint.dto.ErrorResponse;
import com.mycompany.coremaint.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final ObjectMapper objectMapper;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, ObjectMapper objectMapper) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                new AntPathRequestMatcher("/auth/**"),
                                new AntPathRequestMatcher("/swagger-ui.html"),
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                new AntPathRequestMatcher("/h2-console/**")
                        ).permitAll()

                        .requestMatchers(new AntPathRequestMatcher("/api/**"))
                        .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        .anyRequest().hasAuthority("ROLE_ADMIN")
                )

                // 🔹 Manejo de errores 401 y 403
                .exceptionHandling(exception -> exception

                        // Usuario no autenticado
                        .authenticationEntryPoint((request, response, ex) -> {

                            ErrorResponse error = new ErrorResponse(
                                    401,
                                    "Unauthorized",
                                    "No estás autenticado o el token es inválido",
                                    request.getRequestURI()
                            );

                            response.setStatus(401);
                            response.setContentType("application/json");

                            objectMapper.writeValue(response.getOutputStream(), error);
                        })

                        // Usuario sin permisos
                        .accessDeniedHandler((request, response, ex) -> {

                            ErrorResponse error = new ErrorResponse(
                                    403,
                                    "Forbidden",
                                    "No tienes permisos para acceder a este recurso",
                                    request.getRequestURI()
                            );

                            response.setStatus(403);
                            response.setContentType("application/json");

                            objectMapper.writeValue(response.getOutputStream(), error);
                        })
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}