package org.example.turismouq.Repository;

import org.example.turismouq.Model.TipoAlojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAlojamientoRepository extends JpaRepository<TipoAlojamiento, Long> {
    boolean existsByNombre(String nombre);
}
