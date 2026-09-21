package org.example.turismouq.DTO;

import lombok.*;
import org.example.turismouq.Model.Enums.EstadoUsuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSistemaResponseDTO {

    private Long id;
    private String nombre;
    private String correo;
    private EstadoUsuario estado;
    private String cedula;
}
