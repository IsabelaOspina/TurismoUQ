package org.example.turismouq.Service;

import org.example.turismouq.Repository.ReservaPagoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class ReservaPagoService {

    private final ReservaPagoRepository reservaPagoRepository;

    public ReservaPagoService(ReservaPagoRepository reservaPagoRepository) {
        this.reservaPagoRepository = reservaPagoRepository;
    }

    public Map<String, Object> reservarYPagar(
            Long idCliente,
            Long idHabitacion,
            Date fechaIn,
            Date fechaOut,
            Integer numeroPersonas,
            BigDecimal valorPago,
            String metodoPago,
            Long idServicio,
            Integer cantidadServicio
    ) {
        try {
            return reservaPagoRepository.reservarYPagar(
                    idCliente, idHabitacion, fechaIn, fechaOut, numeroPersonas,
                    valorPago, metodoPago, idServicio, cantidadServicio
            );
        } catch (Exception e) {
            throw new RuntimeException(ErrorOracle.mensaje(e));
        }
    }

    public List<Map<String, Object>> serviciosDeHabitacion(Long idHabitacion) {
        return reservaPagoRepository.serviciosDeHabitacion(idHabitacion);
    }
}