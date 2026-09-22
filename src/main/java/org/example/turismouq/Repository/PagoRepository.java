package org.example.turismouq.Repository;

import org.example.turismouq.Model.Enums.MetodoPago;
import org.example.turismouq.Model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByReservaId(Long idReserva);
    List<Pago> findByMetodoPago(MetodoPago metodoPago);
}
