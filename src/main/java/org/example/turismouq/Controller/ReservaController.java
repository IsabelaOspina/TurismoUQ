package org.example.turismouq.Controller;

import org.example.turismouq.Service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearReserva(@RequestBody Map<String, Object> body) {
        try {
            BigDecimal idReserva = reservaService.crearReserva(
                    Long.valueOf(body.get("idCliente").toString()),
                    Long.valueOf(body.get("idHabitacion").toString()),
                    java.sql.Date.valueOf(body.get("fechaIn").toString()),
                    java.sql.Date.valueOf(body.get("fechaOut").toString()),
                    Integer.valueOf(body.get("numeroPersonas").toString())
            );
            return ResponseEntity.ok(Map.of(
                    "idReserva", idReserva,
                    "mensaje", "Reserva creada exitosamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/valor-estadia")
    public ResponseEntity<?> valorEstadia(
            @RequestParam Long idHabitacion,
            @RequestParam String fechaIn,
            @RequestParam String fechaOut) {
        try {
            BigDecimal valor = reservaService.calcularValor(
                    idHabitacion,
                    java.sql.Date.valueOf(fechaIn),
                    java.sql.Date.valueOf(fechaOut)
            );
            return ResponseEntity.ok(Map.of("valor", valor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}