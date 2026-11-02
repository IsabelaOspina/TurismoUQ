package org.example.turismouq.Service;

import org.example.turismouq.Repository.ConcurrenciaRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConcurrenciaService {

    private final ConcurrenciaRepository concurrenciaRepository;

    public ConcurrenciaService(ConcurrenciaRepository concurrenciaRepository) {
        this.concurrenciaRepository = concurrenciaRepository;
    }

    /**
     * Ejecuta una reserva de la prueba y mide cuándo empezó y terminó en el servidor,
     * para poder dibujar las dos sesiones en la misma línea de tiempo.
     * Siempre devuelve un resultado: si Oracle rechaza la reserva, va en "error".
     */
    public Map<String, Object> reservar(
            boolean conBloqueo,
            Long idCliente,
            Long idHabitacion,
            Date fechaIn,
            Date fechaOut,
            Integer numeroPersonas,
            Integer segundosEspera
    ) {
        Map<String, Object> resultado = new LinkedHashMap<>();
        long inicio = System.currentTimeMillis();
        try {
            resultado.putAll(concurrenciaRepository.reservar(
                    conBloqueo, idCliente, idHabitacion, fechaIn, fechaOut, numeroPersonas, segundosEspera
            ));
            resultado.put("ok", true);
        } catch (Exception e) {
            resultado.put("ok", false);
            resultado.put("error", ErrorOracle.mensaje(e));
        }
        resultado.put("inicioMs", inicio);
        resultado.put("finMs", System.currentTimeMillis());
        return resultado;
    }

    public int limpiar(Long idHabitacion, Date fechaIn, Date fechaOut) {
        try {
            return concurrenciaRepository.limpiar(idHabitacion, fechaIn, fechaOut);
        } catch (Exception e) {
            throw new RuntimeException(ErrorOracle.mensaje(e));
        }
    }

    public List<Map<String, Object>> reservasQueSeCruzan(Long idHabitacion, Date fechaIn, Date fechaOut) {
        return concurrenciaRepository.reservasQueSeCruzan(idHabitacion, fechaIn, fechaOut);
    }
}