package org.example.turismouq.Repository;

import org.example.turismouq.Model.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlojamientoRepository extends JpaRepository<Alojamiento, Long> {
    List<Alojamiento> findByMunicipioId(Long idMunicipio);
    List<Alojamiento> findByPropietarioId(Long idPropietario);
    List<Alojamiento> findByTipoAlojamientoId(Long idTipoAlojamiento);
    boolean existsByMunicipioIdAndNombre(Long idMunicipio, String nombre);
}
