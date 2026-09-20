package org.example.turismouq.Model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class TarifaId implements Serializable {

    @Column(name = "id_habitacion")
    private Long idHabitacion;

    @Column(name = "id_temporada")
    private Long idTemporada;
}