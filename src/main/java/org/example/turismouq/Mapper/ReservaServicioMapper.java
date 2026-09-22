package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.ReservaServicioDTO;
import org.example.turismouq.Model.Reserva;
import org.example.turismouq.Model.ReservaServicio;
import org.example.turismouq.Model.ReservaServicioId;
import org.example.turismouq.Model.Servicio;
import org.springframework.stereotype.Component;

@Component
public class ReservaServicioMapper {

    public ReservaServicioDTO toDTO(ReservaServicio entity) {
        if (entity == null) return null;

        return new ReservaServicioDTO(
                entity.getServicio().getId(),
                entity.getReserva().getId(),
                entity.getCantidad(),
                entity.getSubtotal()
        );
    }

    public ReservaServicio toEntity(
            ReservaServicioDTO dto,
            Servicio servicio,
            Reserva reserva
    ) {
        if (dto == null) return null;

        ReservaServicio entity = new ReservaServicio();

        ReservaServicioId id = new ReservaServicioId(
                servicio.getId(),
                reserva.getId()
        );

        entity.setId(id);
        entity.setServicio(servicio);
        entity.setReserva(reserva);
        entity.setCantidad(dto.getCantidad());
        entity.setSubtotal(dto.getSubtotal());

        return entity;
    }
}

