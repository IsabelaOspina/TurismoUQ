package org.example.turismouq.Controller;

import java.time.LocalDate;
import java.util.List;

import org.example.turismouq.Repository.ReporteRepository;
import org.example.turismouq.Service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }


    @GetMapping("/ocupacion-mensual")
    public List<ReporteRepository.OcupacionPorMes> ocupacionPorMunicipioYMes() {
        return reporteService.ocupacionPorMunicipioYMes();
    }


    @GetMapping("/ingresos")
    public List<ReporteRepository.IngresosRollup> ingresosRollup() {
        return reporteService.ingresosRollup();
    }


    @GetMapping("/top-alojamientos")
    public List<ReporteRepository.TopAlojamiento> topAlojamientos() {
        return reporteService.topAlojamientos();
    }


    @GetMapping("/variacion-mensual")
    public List<ReporteRepository.VariacionMensual> variacionMensual() {
        return reporteService.variacionMensual();
    }

    // GET /api/reportes/reservas?fechaInicio=2024-01-01&fechaFin=2024-03-31
    @GetMapping("/reservas")
    public List<ReporteRepository.ReservaDetalle> reservasPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return reporteService.reservasPorFechas(fechaInicio, fechaFin);
    }

    @GetMapping("/ocupacion-mensual-mv")
    public List<ReporteRepository.OcupacionMensualMv> ocupacionMensualMv() {
        return reporteService.ocupacionMensualMv();
    }

    @PostMapping("/ocupacion-mensualv/refrescar")
    public void refrescarOcupacionMensual() {
        reporteService.refrescarOcupacionMensual();
    }

    @GetMapping("/reservas-trimestre")
    public List<ReporteRepository.ReservasPorTrimestre> reservasPorTrimestre() {
        return reporteService.reservasPorTrimestre();
    }

    // GET /api/reportes/mejores-alojamientos
    @GetMapping("/mejores-alojamientos")
    public List<ReporteRepository.AlojamientoCalificado> mejoresAlojamientos() {
        return reporteService.mejoresAlojamientos();
    }
}
