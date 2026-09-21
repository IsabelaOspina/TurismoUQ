package org.example.turismouq.DTO;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.example.turismouq.Model.Enums.MetodoPago;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Long id;
    private LocalDate fecha;
    private BigDecimal valor;
    private Boolean confirmado;
    private MetodoPago metodoPago;
    private Long idReserva;
}