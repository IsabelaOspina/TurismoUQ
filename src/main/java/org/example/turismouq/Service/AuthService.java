package org.example.turismouq.Service;

import org.example.turismouq.DTO.LoginRequest;
import org.example.turismouq.DTO.LoginResponse;
import org.example.turismouq.Model.Enums.EstadoUsuario;
import org.example.turismouq.Model.UsuarioSistema;
import org.example.turismouq.Repository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioSistemaRepository usuarioRepository;

    public AuthService(UsuarioSistemaRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponse iniciarSesion(LoginRequest request) {

        UsuarioSistema usuario = usuarioRepository
                .findByCorreo(request.getCorreo())
                .orElseThrow(() ->
                        new RuntimeException("Correo o contraseña incorrectos"));

        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new RuntimeException("El usuario se encuentra inactivo");
        }

        if (!request.getContrasena().equals(usuario.getContrasena())) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }

        return new LoginResponse(
                "Inicio de sesión exitoso",
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo()
        );
    }
}
