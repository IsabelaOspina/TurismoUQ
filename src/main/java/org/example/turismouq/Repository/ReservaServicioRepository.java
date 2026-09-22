package org.example.turismouq.Repository;

import org.example.turismouq.Model.ReservaServicio;
import org.example.turismouq.Model.ReservaServicioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaServicioRepository extends JpaRepository<ReservaServicio, ReservaServicioId> {
    List<ReservaServicio> findByReservaId(Long idReserva);
    List<ReservaServicio> findByServicioId(Long idServicio);
}

