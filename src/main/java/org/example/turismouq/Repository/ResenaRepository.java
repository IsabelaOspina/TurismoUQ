package org.example.turismouq.Repository;

import org.example.turismouq.Model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    Optional<Resena> findByReservaId(Long idReserva);
    boolean existsByReservaId(Long idReserva);
}