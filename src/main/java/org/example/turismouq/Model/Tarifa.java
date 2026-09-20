package org.example.turismouq.Model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TARIFA")
@Getter @Setter @NoArgsConstructor
public class Tarifa {

    @EmbeddedId
    private TarifaId id = new TarifaId();

    @MapsId("idHabitacion")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_habitacion")
    private Habitacion habitacion;

    @MapsId("idTemporada")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_temporada")
    private Temporada temporada;

    @Column(name = "precio_noche", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioNoche;
}