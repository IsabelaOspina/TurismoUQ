package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.ServicioDTO;
import org.example.turismouq.Model.Alojamiento;
import org.example.turismouq.Model.Servicio;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper {

    public ServicioDTO toDTO(Servicio entity) {
        if (entity == null) return null;

        return new ServicioDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecio(),
                entity.getAlojamiento().getId()
        );
    }

    public Servicio toEntity(ServicioDTO dto, Alojamiento alojamiento) {
        if (dto == null) return null;

        Servicio entity = new Servicio();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setAlojamiento(alojamiento);

        return entity;
    }
}