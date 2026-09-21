package org.example.turismouq.DTO;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.example.turismouq.Model.Enums.EstadoReserva;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Long id;
    private LocalDate fechaReserva;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private Integer numeroPersonas;
    private EstadoReserva estado;
    private BigDecimal valorTotal;

    private Long idCliente;

    private Set<Long> idHabitaciones = new HashSet<>();
}