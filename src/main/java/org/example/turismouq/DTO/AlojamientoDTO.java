package org.example.turismouq.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlojamientoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private Integer capacidad;
    private String telefono;
    private String correo;

    private Long idPropietario;
    private Long idTipoAlojamiento;
    private Long idMunicipio;
}