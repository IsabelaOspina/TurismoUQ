package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.ClienteDTO;
import org.example.turismouq.Model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente entity) {
        if (entity == null) return null;

        return new ClienteDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getCedula(),
                entity.getCorreo()
        );
    }

    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;

        Cliente entity = new Cliente();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setCedula(dto.getCedula());
        entity.setCorreo(dto.getCorreo());

        return entity;
    }
}