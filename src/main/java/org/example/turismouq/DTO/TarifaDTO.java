package org.example.turismouq.DTO;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaDTO {

    private Long idHabitacion;
    private Long idTemporada;
    private BigDecimal precioNoche;
}