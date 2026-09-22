package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.ResenaDTO;
import org.example.turismouq.Model.Resena;
import org.example.turismouq.Model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ResenaMapper {

    public ResenaDTO toDTO(Resena entity) {
        if (entity == null) return null;

        return new ResenaDTO(
                entity.getId(),
                entity.getCalificacion(),
                entity.getComentario(),
                entity.getFecha(),
                entity.getReserva().getId()
        );
    }

    public Resena toEntity(ResenaDTO dto, Reserva reserva) {
        if (dto == null) return null;

        Resena entity = new Resena();
        entity.setId(dto.getId());
        entity.setCalificacion(dto.getCalificacion());
        entity.setComentario(dto.getComentario());
        entity.setFecha(dto.getFecha());
        entity.setReserva(reserva);

        return entity;
    }
}