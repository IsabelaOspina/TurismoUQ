package org.example.turismouq.Service;

import org.example.turismouq.Repository.LiquidacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LiquidacionService {

    private final LiquidacionRepository liquidacionRepository;

    public LiquidacionService(LiquidacionRepository liquidacionRepository) {
        this.liquidacionRepository = liquidacionRepository;
    }

    /** Corre el proceso masivo y devuelve el resultado guardado. */
    public List<Map<String, Object>> generar(int anio, int mes) {
        try {
            liquidacionRepository.generar(anio, mes);
        } catch (Exception e) {
            throw new RuntimeException(ErrorOracle.mensaje(e));
        }
        return liquidacionRepository.consultar(anio, mes);
    }

    public List<Map<String, Object>> consultar(int anio, int mes) {
        return liquidacionRepository.consultar(anio, mes);
    }
}
