
package org.example.turismouq.Service;

import org.example.turismouq.Entity.AuditoriaTarifa;
import org.example.turismouq.Repository.AuditoriaTarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaTarifaService {

    @Autowired
    private AuditoriaTarifaRepository auditoriaTarifaRepository;

    public List<AuditoriaTarifa> listarAuditoria() {
        return auditoriaTarifaRepository.findAllByOrderByFechaCambioDesc();
    }
}
