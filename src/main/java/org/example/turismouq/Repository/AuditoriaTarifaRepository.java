
package org.example.turismouq.Repository;

import org.example.turismouq.Entity.AuditoriaTarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriaTarifaRepository
        extends JpaRepository<AuditoriaTarifa, Long> {

    List<AuditoriaTarifa> findAllByOrderByFechaCambioDesc();

}
