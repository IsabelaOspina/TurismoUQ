package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.ReservaDTO;
import org.example.turismouq.Model.Cliente;
import org.example.turismouq.Model.Habitacion;
import org.example.turismouq.Model.Reserva;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReservaMapper {

    public ReservaDTO toDTO(Reserva entity) {
        if (entity == null) return null;

        Set<Long> idHabitaciones = entity.getHabitaciones() == null
                ? Collections.emptySet()
                : entity.getHabitaciones()
                .stream()
                .map(Habitacion::getId)
                .collect(Collectors.toSet());

        return new ReservaDTO(
                entity.getId(),
                entity.getFechaReserva(),
                entity.getFechaEntrada(),
                entity.getFechaSalida(),
                entity.getNumeroPersonas(),
                entity.getEstado(),
                entity.getValorTotal(),
                entity.getCliente().getId(),
                idHabitaciones
        );
    }

    public Reserva toEntity(
            ReservaDTO dto,
            Cliente cliente,
            Set<Habitacion> habitaciones
    ) {
        if (dto == null) return null;

        Reserva entity = new Reserva();
        entity.setId(dto.getId());
        entity.setFechaReserva(dto.getFechaReserva());
        entity.setFechaEntrada(dto.getFechaEntrada());
        entity.setFechaSalida(dto.getFechaSalida());
        entity.setNumeroPersonas(dto.getNumeroPersonas());
        entity.setEstado(dto.getEstado());
        entity.setValorTotal(dto.getValorTotal());
        entity.setCliente(cliente);
        entity.setHabitaciones(habitaciones);

        return entity;
    }
}

