package org.example.turismouq.Model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SERVICIO",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_alojamiento", "nombre"}))
@Getter @Setter @NoArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_servicio")
    @SequenceGenerator(name = "seq_servicio", sequenceName = "SEQ_SERVICIO", allocationSize = 1)
    @Column(name = "id_servicio")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_alojamiento", nullable = false)
    private Alojamiento alojamiento;
}
