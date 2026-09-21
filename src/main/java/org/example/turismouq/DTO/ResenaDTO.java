package org.example.turismouq.DTO;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenaDTO {

    private Long id;
    private Integer calificacion;
    private String comentario;
    private LocalDate fecha;
    private Long idReserva;
}