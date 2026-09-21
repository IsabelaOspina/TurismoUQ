package org.example.turismouq.DTO;

import lombok.*;
import java.math.BigDecimal;
import org.example.turismouq.Model.Enums.TipoHabitacion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionDTO {

    private Long id;
    private String numero;
    private Integer capacidad;
    private BigDecimal precioBase;
    private TipoHabitacion tipo;
    private Long idAlojamiento;
}