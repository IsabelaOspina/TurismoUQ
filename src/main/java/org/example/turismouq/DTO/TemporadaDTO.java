package org.example.turismouq.DTO;

import lombok.*;
import java.time.LocalDate;
import org.example.turismouq.Model.Enums.TipoTemporada;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemporadaDTO {

    private Long id;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private TipoTemporada tipo;
}
