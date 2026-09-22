package org.example.turismouq.Repository;

import org.example.turismouq.Model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByAlojamientoId(Long idAlojamiento);
    boolean existsByAlojamientoIdAndNombre(Long idAlojamiento, String nombre);
}

