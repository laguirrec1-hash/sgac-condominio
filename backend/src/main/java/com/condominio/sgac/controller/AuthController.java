package com.condominio.sgac.controller;

import com.condominio.sgac.dto.ApiResponse;
import com.condominio.sgac.dto.AuthRequest;
import com.condominio.sgac.dto.AuthResponse;
import com.condominio.sgac.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok("Autenticación exitosa", response));
    }
}
