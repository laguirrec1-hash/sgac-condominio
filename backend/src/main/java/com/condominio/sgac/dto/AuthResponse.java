package com.condominio.sgac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String tipoToken = "Bearer";
    private Long id;
    private String nombre;
    private String correo;
    private List<String> roles;
}
