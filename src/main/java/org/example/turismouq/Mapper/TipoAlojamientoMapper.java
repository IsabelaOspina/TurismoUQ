package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.TipoAlojamientoDTO;
import org.example.turismouq.Model.TipoAlojamiento;
import org.springframework.stereotype.Component;

@Component
public class TipoAlojamientoMapper {

    public TipoAlojamientoDTO toDTO(TipoAlojamiento entity) {
        if (entity == null) return null;

        return new TipoAlojamientoDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion()
        );
    }

    public TipoAlojamiento toEntity(TipoAlojamientoDTO dto) {
        if (dto == null) return null;

        TipoAlojamiento entity = new TipoAlojamiento();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        return entity;
    }
}