package org.example.turismouq.DTO;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaServicioDTO {

    private Long idServicio;
    private Long idReserva;
    private Integer cantidad;
    private BigDecimal subtotal;
}
