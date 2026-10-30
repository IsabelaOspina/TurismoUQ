
package org.example.turismouq.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditarReservaDTO {

    private Long idCliente;
    private Long idHabitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private Integer numeroPersonas;

}
