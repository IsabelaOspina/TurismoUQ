package org.example.turismouq.Repository;

import org.example.turismouq.Model.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Long> {
    Optional<UsuarioSistema> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    boolean existsByCedula(String cedula);
}
