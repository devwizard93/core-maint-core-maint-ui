package com.mycompany.coremaint.controller;

import com.mycompany.coremaint.model.User;
import com.mycompany.coremaint.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        String msg = authService.register(user);
        Map<String, String> response = Map.of("message", msg);
        if (msg.equals("Usuario registrado correctamente")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        Map<String, String> result = authService.login(user);
        if ("Login correcto".equals(result.get("message"))) {
            log.info("Resultado del login: {}", result);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(401).body(result);
        }
    }

}