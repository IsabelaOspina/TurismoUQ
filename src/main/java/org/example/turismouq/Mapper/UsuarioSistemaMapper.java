package org.example.turismouq.Mapper;

import org.example.turismouq.DTO.UsuarioSistemaRequestDTO;
import org.example.turismouq.DTO.UsuarioSistemaResponseDTO;
import org.example.turismouq.Model.UsuarioSistema;
import org.springframework.stereotype.Component;

@Component
public class UsuarioSistemaMapper {

    public UsuarioSistemaResponseDTO toDTO(UsuarioSistema entity) {
        if (entity == null) return null;

        return new UsuarioSistemaResponseDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getCorreo(),
                entity.getEstado(),
                entity.getCedula()
        );
    }

    public UsuarioSistema toEntity(UsuarioSistemaRequestDTO dto) {
        if (dto == null) return null;

        UsuarioSistema entity = new UsuarioSistema();
        entity.setNombre(dto.getNombre());
        entity.setCorreo(dto.getCorreo());
        entity.setEstado(dto.getEstado());
        entity.setCedula(dto.getCedula());
        entity.setContrasena(dto.getContrasena());

        return entity;
    }
}