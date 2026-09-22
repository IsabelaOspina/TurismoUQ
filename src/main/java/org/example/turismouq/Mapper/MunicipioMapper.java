package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.MunicipioDTO;
import org.example.turismouq.Model.Municipio;
import org.springframework.stereotype.Component;

@Component
public class MunicipioMapper {

    public MunicipioDTO toDTO(Municipio entity) {
        if (entity == null) return null;

        return new MunicipioDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion()
        );
    }

    public Municipio toEntity(MunicipioDTO dto) {
        if (dto == null) return null;

        Municipio entity = new Municipio();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        return entity;
    }
}
