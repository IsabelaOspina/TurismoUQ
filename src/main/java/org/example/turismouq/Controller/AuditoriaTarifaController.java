
package org.example.turismouq.Controller;

import org.example.turismouq.Entity.AuditoriaTarifa;
import org.example.turismouq.Service.AuditoriaTarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria-tarifas")
@CrossOrigin(origins = "*")
public class AuditoriaTarifaController {

    @Autowired
    private AuditoriaTarifaService auditoriaTarifaService;

    @GetMapping
    public ResponseEntity<List<AuditoriaTarifa>> listarAuditoria() {

        List<AuditoriaTarifa> auditorias =
                auditoriaTarifaService.listarAuditoria();

        return ResponseEntity.ok(auditorias);
    }
}
