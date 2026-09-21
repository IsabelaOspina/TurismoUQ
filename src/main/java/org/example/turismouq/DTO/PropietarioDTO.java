package org.example.turismouq.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropietarioDTO {

    private Long id;
    private String nombre;
    private String cedula;
    private String correo;
}