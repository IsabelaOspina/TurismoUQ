package org.example.turismouq.Repository;


import org.example.turismouq.Model.Enums.TipoHabitacion;
import org.example.turismouq.Model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByAlojamientoId(Long idAlojamiento);
    List<Habitacion> findByTipo(TipoHabitacion tipo);
    boolean existsByAlojamientoIdAndNumero(Long idAlojamiento, String numero);
}

