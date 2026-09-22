package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.HabitacionDTO;
import org.example.turismouq.Model.Alojamiento;
import org.example.turismouq.Model.Habitacion;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper {

    public HabitacionDTO toDTO(Habitacion entity) {
        if (entity == null) return null;

        return new HabitacionDTO(
                entity.getId(),
                entity.getNumero(),
                entity.getCapacidad(),
                entity.getPrecioBase(),
                entity.getTipo(),
                entity.getAlojamiento().getId()
        );
    }

    public Habitacion toEntity(HabitacionDTO dto, Alojamiento alojamiento) {
        if (dto == null) return null;

        Habitacion entity = new Habitacion();
        entity.setId(dto.getId());
        entity.setNumero(dto.getNumero());
        entity.setCapacidad(dto.getCapacidad());
        entity.setPrecioBase(dto.getPrecioBase());
        entity.setTipo(dto.getTipo());
        entity.setAlojamiento(alojamiento);

        return entity;
    }
}

