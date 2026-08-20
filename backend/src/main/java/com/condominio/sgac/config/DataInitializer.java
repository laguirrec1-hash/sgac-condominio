package com.condominio.sgac.config;

import com.condominio.sgac.model.Rol;
import com.condominio.sgac.model.Usuario;
import com.condominio.sgac.repository.RolRepository;
import com.condominio.sgac.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Rol rolAdmin = crearRolSiNoExiste("ROLE_ADMIN");
        crearRolSiNoExiste("ROLE_RESIDENTE");
        crearRolSiNoExiste("ROLE_CONSERJE");

        if (!usuarioRepository.existsByCorreo("admin@condominio.com")) {
            Usuario admin = Usuario.builder()
                    .nombre("Administrador")
                    .apellido("Principal")
                    .identificacion("ADM-001")
                    .correo("admin@condominio.com")
                    .telefono("0000000000")
                    .password(passwordEncoder.encode("admin123"))
                    .activo(true)
                    .roles(Set.of(rolAdmin))
                    .build();
            usuarioRepository.save(admin);
        }
    }

    private Rol crearRolSiNoExiste(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseGet(() -> rolRepository.save(Rol.builder().nombre(nombre).build()));
    }
}
