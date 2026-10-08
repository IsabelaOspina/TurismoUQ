package org.example.turismouq.Service;

import org.example.turismouq.Repository.ReservaProcedimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class ReservaService {

    @Autowired
    private ReservaProcedimientosRepository reservaProcedureRepository;

    public BigDecimal crearReserva(Long idCliente, Long idHabitacion,
                                   Date fechaIn, Date fechaOut, Integer numeroPersonas) {
        try {
            return reservaProcedureRepository.crearReserva(
                    idCliente, idHabitacion, fechaIn, fechaOut, numeroPersonas
            );
        } catch (Exception e) {
            // Extraer el mensaje de ORA-20100, ORA-20101, ORA-20102, etc.
            String mensaje = e.getMessage();
            if (mensaje != null && mensaje.contains("ORA-")) {
                throw new RuntimeException(mensaje.substring(mensaje.indexOf("ORA-")));
            }
            throw new RuntimeException("Error al crear reserva: " + mensaje);
        }
    }

    public BigDecimal calcularValor(Long idHabitacion, Date fechaIn, Date fechaOut) {
        return reservaProcedureRepository.calcularValorEstadia(idHabitacion, fechaIn, fechaOut);
    }
}