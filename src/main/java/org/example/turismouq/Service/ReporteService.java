package org.example.turismouq.Service;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import org.example.turismouq.Repository.ReporteRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public List<ReporteRepository.OcupacionPorMes> ocupacionPorMunicipioYMes() {
        return reporteRepository.ocupacionPorMunicipioYMes();
    }

    public List<ReporteRepository.IngresosRollup> ingresosRollup() {
        return reporteRepository.ingresosRollup();
    }

    public List<ReporteRepository.TopAlojamiento> topAlojamientos() {
        return reporteRepository.top3AlojamientosPorMunicipio();
    }

    public List<ReporteRepository.VariacionMensual> variacionMensual() {
        return reporteRepository.variacionMensual();
    }

    public List<ReporteRepository.ReservaDetalle> reservasPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("fechaFin no puede ser anterior a fechaInicio");
        }
        return reporteRepository.reservasPorFechas(fechaInicio, fechaFin);
    }

    public List<ReporteRepository.OcupacionMensualMv> ocupacionMensualMv() {
        return reporteRepository.ocupacionMensual();
    }

    public void refrescarOcupacionMensual() {
        reporteRepository.refrescarOcupacionMensual();
    }

    public List<ReporteRepository.ReservasPorTrimestre> reservasPorTrimestre() {
        return reporteRepository.reservasPorTrimestre();
    }

    public List<ReporteRepository.AlojamientoCalificado> mejoresAlojamientos() {
        return reporteRepository.mejoresAlojamientos();
    }
}