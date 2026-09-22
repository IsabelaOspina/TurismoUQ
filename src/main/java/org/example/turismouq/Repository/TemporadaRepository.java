package org.example.turismouq.Repository;

import org.example.turismouq.Model.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long> {
    boolean existsByNombre(String nombre);
}
