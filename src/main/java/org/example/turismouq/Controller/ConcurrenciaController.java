package org.example.turismouq.Controller;

import org.example.turismouq.Service.ConcurrenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/concurrencia")
@CrossOrigin(origins = "*")
public class ConcurrenciaController {

    private final ConcurrenciaService concurrenciaService;

    public ConcurrenciaController(ConcurrenciaService concurrenciaService) {
        this.concurrenciaService = concurrenciaService;
    }

    @PostMapping("/reservar")
    public ResponseEntity<?> reservar(@RequestBody Map<String, Object> body) {
        try {
            return ResponseEntity.ok(concurrenciaService.reservar(
                    Boolean.parseBoolean(body.get("conBloqueo").toString()),
                    Long.valueOf(body.get("idCliente").toString()),
                    Long.valueOf(body.get("idHabitacion").toString()),
                    Date.valueOf(body.get("fechaIn").toString()),
                    Date.valueOf(body.get("fechaOut").toString()),
                    Integer.valueOf(body.get("numeroPersonas").toString()),
                    Integer.valueOf(body.get("segundosEspera").toString())
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan datos para la prueba."));
        }
    }

    @PostMapping("/limpiar")
    public ResponseEntity<?> limpiar(@RequestBody Map<String, Object> body) {
        try {
            int borradas = concurrenciaService.limpiar(
                    Long.valueOf(body.get("idHabitacion").toString()),
                    Date.valueOf(body.get("fechaIn").toString()),
                    Date.valueOf(body.get("fechaOut").toString())
            );
            return ResponseEntity.ok(Map.of("borradas", borradas));
        } catch (Exception e) {
            String mensaje = e.getMessage() != null ? e.getMessage() : "No se pudo limpiar la prueba.";
            return ResponseEntity.badRequest().body(Map.of("error", mensaje));
        }
    }

    @GetMapping("/reservas")
    public ResponseEntity<?> reservas(
            @RequestParam Long idHabitacion,
            @RequestParam String fechaIn,
            @RequestParam String fechaOut
    ) {
        return ResponseEntity.ok(concurrenciaService.reservasQueSeCruzan(
                idHabitacion, Date.valueOf(fechaIn), Date.valueOf(fechaOut)
        ));
    }
}