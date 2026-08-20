package com.condominio.sgac.service;

import com.condominio.sgac.dto.AuthRequest;
import com.condominio.sgac.dto.AuthResponse;
import com.condominio.sgac.model.Usuario;
import com.condominio.sgac.repository.UsuarioRepository;
import com.condominio.sgac.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UsuarioRepository usuarioRepository;

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generarToken(request.getCorreo());

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado tras autenticación"));

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return AuthResponse.builder()
                .token(jwt)
                .tipoToken("Bearer")
                .id(usuario.getId())
                .nombre(usuario.getNombre() + " " + usuario.getApellido())
                .correo(usuario.getCorreo())
                .roles(roles)
                .build();
    }
}
