package org.example.turismouq.Repository;

import org.example.turismouq.Model.Tarifa;
import org.example.turismouq.Model.TarifaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, TarifaId> {
}
