package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.AlojamientoDTO;
import org.example.turismouq.Model.Alojamiento;
import org.example.turismouq.Model.Municipio;
import org.example.turismouq.Model.Propietario;
import org.example.turismouq.Model.TipoAlojamiento;
import org.springframework.stereotype.Component;

@Component
public class AlojamientoMapper {

    public AlojamientoDTO toDTO(Alojamiento entity) {
        if (entity == null) return null;

        return new AlojamientoDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getDireccion(),
                entity.getCapacidad(),
                entity.getTelefono(),
                entity.getCorreo(),
                entity.getPropietario().getId(),
                entity.getTipoAlojamiento().getId(),
                entity.getMunicipio().getId()
        );
    }

    public Alojamiento toEntity(
            AlojamientoDTO dto,
            Propietario propietario,
            TipoAlojamiento tipoAlojamiento,
            Municipio municipio
    ) {
        if (dto == null) return null;

        Alojamiento entity = new Alojamiento();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setDireccion(dto.getDireccion());
        entity.setCapacidad(dto.getCapacidad());
        entity.setTelefono(dto.getTelefono());
        entity.setCorreo(dto.getCorreo());
        entity.setPropietario(propietario);
        entity.setTipoAlojamiento(tipoAlojamiento);
        entity.setMunicipio(municipio);

        return entity;
    }
}

