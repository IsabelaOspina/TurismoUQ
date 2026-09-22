package org.example.turismouq.Repository;

import org.example.turismouq.Model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    boolean existsByNombre(String nombre);
}

