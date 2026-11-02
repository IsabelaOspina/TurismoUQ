package org.example.turismouq.Service;

import org.example.turismouq.Repository.ReservaConsultaRepository;
import org.example.turismouq.Repository.ReservaProcedimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaProcedimientosRepository reservaProcedureRepository;

    @Autowired
    private ReservaConsultaRepository reservaConsultaRepository;

    // Consultar una reserva por su número (para precargar "Editar reserva")
    public Optional<Map<String, Object>> buscarReserva(Long idReserva) {
        return reservaConsultaRepository.buscarPorId(idReserva);
    }

    // Crear una reserva
    public BigDecimal crearReserva(
            Long idCliente,
            Long idHabitacion,
            Date fechaIn,
            Date fechaOut,
            Integer numeroPersonas
    ) {
        try {
            return reservaProcedureRepository.crearReserva(
                    idCliente, idHabitacion, fechaIn, fechaOut, numeroPersonas
            );
        } catch (Exception e) {
            throw new RuntimeException(ErrorOracle.mensaje(e));
        }
    }

    // Editar una reserva
    public void editarReserva(
            Long idReserva,
            Long idCliente,
            Long idHabitacion,
            Date fechaIn,
            Date fechaOut,
            Integer numeroPersonas
    ) {
        try {
            reservaProcedureRepository.editarReserva(
                    idReserva,
                    idCliente,
                    idHabitacion,
                    fechaIn,
                    fechaOut,
                    numeroPersonas
            );
        } catch (Exception e) {
            throw new RuntimeException(ErrorOracle.mensaje(e));
        }
    }

    // Calcular el valor de una estadía
    public BigDecimal calcularValor(
            Long idHabitacion,
            Date fechaIn,
            Date fechaOut
    ) {
        try {
            return reservaProcedureRepository.calcularValorEstadia(
                    idHabitacion, fechaIn, fechaOut
            );
        } catch (Exception e) {
            throw new RuntimeException(ErrorOracle.mensaje(e));
        }
    }
}