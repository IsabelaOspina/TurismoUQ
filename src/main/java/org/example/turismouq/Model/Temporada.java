package org.example.turismouq.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import org.example.turismouq.Model.Enums.*;

@Entity
@Table(name = "TEMPORADA")
@Getter @Setter @NoArgsConstructor
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_temporada")
    @SequenceGenerator(name = "seq_temporada", sequenceName = "SEQ_TEMPORADA", allocationSize = 1)
    @Column(name = "id_temporada")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoTemporada tipo;
}