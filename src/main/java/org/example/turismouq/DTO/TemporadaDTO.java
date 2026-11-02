package org.example.turismouq.DTO;

import lombok.*;
import org.example.turismouq.Model.Enums.TipoTemporada;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemporadaDTO {

    private Long id;
    private String nombre;
    private Integer mes;
    private TipoTemporada tipo;
}