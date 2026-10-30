
package org.example.turismouq.Controller;

import org.example.turismouq.DTO.EditarReservaDTO;
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

    // Crear una reserva
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
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }

    // Editar una reserva
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarReserva(
            @PathVariable Long id,
            @RequestBody EditarReservaDTO dto
    ) {
        try {
            reservaService.editarReserva(
                    id,
                    dto.getIdCliente(),
                    dto.getIdHabitacion(),
                    java.sql.Date.valueOf(dto.getFechaEntrada()),
                    java.sql.Date.valueOf(dto.getFechaSalida()),
                    dto.getNumeroPersonas()
            );

            return ResponseEntity.ok(Map.of(
                    "idReserva", id,
                    "mensaje", "Reserva actualizada exitosamente"
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }

    // Calcular el valor de una estadía
    @GetMapping("/valor-estadia")
    public ResponseEntity<?> valorEstadia(
            @RequestParam Long idHabitacion,
            @RequestParam String fechaIn,
            @RequestParam String fechaOut
    ) {
        try {
            BigDecimal valor = reservaService.calcularValor(
                    idHabitacion,
                    java.sql.Date.valueOf(fechaIn),
                    java.sql.Date.valueOf(fechaOut)
            );

            return ResponseEntity.ok(Map.of("valor", valor));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }
}
