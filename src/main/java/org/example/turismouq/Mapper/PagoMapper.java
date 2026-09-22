package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.PagoDTO;
import org.example.turismouq.Model.Pago;
import org.example.turismouq.Model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public PagoDTO toDTO(Pago entity) {
        if (entity == null) return null;

        return new PagoDTO(
                entity.getId(),
                entity.getFecha(),
                entity.getValor(),
                entity.getConfirmado(),
                entity.getMetodoPago(),
                entity.getReserva().getId()
        );
    }

    public Pago toEntity(PagoDTO dto, Reserva reserva) {
        if (dto == null) return null;

        Pago entity = new Pago();
        entity.setId(dto.getId());
        entity.setFecha(dto.getFecha());
        entity.setValor(dto.getValor());
        entity.setConfirmado(dto.getConfirmado());
        entity.setMetodoPago(dto.getMetodoPago());
        entity.setReserva(reserva);

        return entity;
    }
}
