package org.example.turismouq.Repository;

import org.example.turismouq.Model.Enums.EstadoReserva;
import org.example.turismouq.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByClienteId(Long idCliente);
    List<Reserva> findByEstado(EstadoReserva estado);
    List<Reserva> findByFechaEntradaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}