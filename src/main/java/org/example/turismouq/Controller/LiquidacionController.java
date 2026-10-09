package org.example.turismouq.Controller;

import org.example.turismouq.Service.LiquidacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/liquidacion")
public class LiquidacionController {

    private final LiquidacionService liquidacionService;

    public LiquidacionController(LiquidacionService liquidacionService) {
        this.liquidacionService = liquidacionService;
    }

    @GetMapping
    public ResponseEntity<?> consultar(@RequestParam int anio, @RequestParam int mes) {
        return ResponseEntity.ok(liquidacionService.consultar(anio, mes));
    }

    @PostMapping("/generar")
    public ResponseEntity<?> generar(@RequestParam int anio, @RequestParam int mes) {
        try {
            return ResponseEntity.ok(liquidacionService.generar(anio, mes));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
