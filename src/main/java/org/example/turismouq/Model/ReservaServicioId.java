package org.example.turismouq.Model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class ReservaServicioId implements Serializable {

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_reserva")
    private Long idReserva;
}
