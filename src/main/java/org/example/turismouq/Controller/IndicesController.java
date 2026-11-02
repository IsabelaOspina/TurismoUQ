package org.example.turismouq.Controller;

import org.example.turismouq.Service.IndicesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/indices")
@CrossOrigin(origins = "*")
public class IndicesController {

    private final IndicesService indicesService;

    public IndicesController(IndicesService indicesService) {
        this.indicesService = indicesService;
    }

    @GetMapping
    public ResponseEntity<?> casos() {
        return ResponseEntity.ok(indicesService.casos());
    }

    @PostMapping("/{id}/comparar")
    public ResponseEntity<?> comparar(@PathVariable int id) {
        try {
            return ResponseEntity.ok(indicesService.comparar(id));
        } catch (Exception e) {
            String mensaje = e.getMessage() != null ? e.getMessage() : "No se pudo comparar la consulta.";
            return ResponseEntity.badRequest().body(Map.of("error", mensaje));
        }
    }
}
