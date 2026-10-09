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
            // Deja solo "ORA-20100: ...", "ORA-20101: ...", "ORA-20102: ...", etc.
            throw new RuntimeException(ErrorOracle.mensaje(e));
        }
    }

    public BigDecimal calcularValor(Long idHabitacion, Date fechaIn, Date fechaOut) {
        try {
            return reservaProcedureRepository.calcularValorEstadia(idHabitacion, fechaIn, fechaOut);
        } catch (Exception e) {
            throw new RuntimeException(ErrorOracle.mensaje(e));
        }
    }
}