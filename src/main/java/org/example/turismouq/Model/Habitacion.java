package org.example.turismouq.Model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import org.example.turismouq.Model.Enums.*;

@Entity
@Table(name = "HABITACION",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_alojamiento", "numero"}))
@Getter @Setter @NoArgsConstructor
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_habitacion")
    @SequenceGenerator(name = "seq_habitacion", sequenceName = "SEQ_HABITACION", allocationSize = 1)
    @Column(name = "id_habitacion")
    private Long id;

    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "precio_base", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioBase;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoHabitacion tipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_alojamiento", nullable = false)
    private Alojamiento alojamiento;
}