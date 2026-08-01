package com.mycompany.coremaint.service;

import com.mycompany.coremaint.model.User;
import com.mycompany.coremaint.repository.UserRepository;
import com.mycompany.coremaint.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository repo, PasswordEncoder encoder, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    public String register(User user) {
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            return "El usuario ya existe";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        repo.save(user);
        return "Usuario registrado correctamente";
    }

    public Map<String, String> login(User user) {
        //pertenece a spring Security
        //Ese código verifica si el username y password son correctos para iniciar sesión.
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // ✅ Obtiene los detalles del usuario autenticado
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // ✅ Genera el token con roles y username
            String token = jwtUtil.generateToken(userDetails);

            return Map.of(
                    "message", "Login correcto",
                    "token", token
            );
        }

        return Map.of("message", "Login fallido");
    }
}
