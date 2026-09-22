package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.PropietarioDTO;
import org.example.turismouq.Model.Propietario;
import org.springframework.stereotype.Component;

@Component
public class PropietarioMapper {

    public PropietarioDTO toDTO(Propietario entity) {
        if (entity == null) return null;

        return new PropietarioDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getCedula(),
                entity.getCorreo()
        );
    }

    public Propietario toEntity(PropietarioDTO dto) {
        if (dto == null) return null;

        Propietario entity = new Propietario();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setCedula(dto.getCedula());
        entity.setCorreo(dto.getCorreo());

        return entity;
    }
}
