package org.example.turismouq.Controller;

import org.example.turismouq.Service.ReservaPagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas-pago")
@CrossOrigin(origins = "*")
public class ReservaPagoController {

    private final ReservaPagoService reservaPagoService;

    public ReservaPagoController(ReservaPagoService reservaPagoService) {
        this.reservaPagoService = reservaPagoService;
    }

    // GET /api/reservas-pago/servicios?idHabitacion=2
    @GetMapping("/servicios")
    public ResponseEntity<?> servicios(@RequestParam Long idHabitacion) {
        return ResponseEntity.ok(reservaPagoService.serviciosDeHabitacion(idHabitacion));
    }

    // POST /api/reservas-pago
    @PostMapping
    public ResponseEntity<?> reservarYPagar(@RequestBody Map<String, Object> body) {
        try {
            Object servicio = body.get("idServicio");
            Object cantidad = body.get("cantidadServicio");

            Map<String, Object> resultado = reservaPagoService.reservarYPagar(
                    Long.valueOf(body.get("idCliente").toString()),
                    Long.valueOf(body.get("idHabitacion").toString()),
                    java.sql.Date.valueOf(body.get("fechaIn").toString()),
                    java.sql.Date.valueOf(body.get("fechaOut").toString()),
                    Integer.valueOf(body.get("numeroPersonas").toString()),
                    new BigDecimal(body.get("valorPago").toString()),
                    body.get("metodoPago").toString(),
                    servicio == null ? null : Long.valueOf(servicio.toString()),
                    cantidad == null ? null : Integer.valueOf(cantidad.toString())
            );
            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            String mensaje = e.getMessage() != null ? e.getMessage() : "Faltan datos para registrar la reserva.";
            return ResponseEntity.badRequest().body(Map.of("error", mensaje));
        }
    }
}