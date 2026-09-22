package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.TemporadaDTO;
import org.example.turismouq.Model.Temporada;
import org.springframework.stereotype.Component;

@Component
public class TemporadaMapper {

    public TemporadaDTO toDTO(Temporada entity) {
        if (entity == null) return null;

        return new TemporadaDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getTipo()
        );
    }

    public Temporada toEntity(TemporadaDTO dto) {
        if (dto == null) return null;

        Temporada entity = new Temporada();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setTipo(dto.getTipo());

        return entity;
    }
}

