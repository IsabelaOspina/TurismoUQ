package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.TarifaDTO;
import org.example.turismouq.Model.Habitacion;
import org.example.turismouq.Model.Tarifa;
import org.example.turismouq.Model.TarifaId;
import org.example.turismouq.Model.Temporada;
import org.springframework.stereotype.Component;

@Component
public class TarifaMapper {

    public TarifaDTO toDTO(Tarifa entity) {
        if (entity == null) return null;

        return new TarifaDTO(
                entity.getHabitacion().getId(),
                entity.getTemporada().getId(),
                entity.getPrecioNoche()
        );
    }

    public Tarifa toEntity(
            TarifaDTO dto,
            Habitacion habitacion,
            Temporada temporada
    ) {
        if (dto == null) return null;

        Tarifa entity = new Tarifa();

        TarifaId id = new TarifaId(
                habitacion.getId(),
                temporada.getId()
        );

        entity.setId(id);
        entity.setHabitacion(habitacion);
        entity.setTemporada(temporada);
        entity.setPrecioNoche(dto.getPrecioNoche());

        return entity;
    }
}
