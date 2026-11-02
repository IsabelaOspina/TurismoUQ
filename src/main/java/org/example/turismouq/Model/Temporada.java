package org.example.turismouq.Model;


import jakarta.persistence.*;
import lombok.*;
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

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "mes", nullable = false, unique = true)
    private Integer mes;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoTemporada tipo;
}