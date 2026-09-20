package org.example.turismouq.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "RESENA")
@Getter @Setter @NoArgsConstructor
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_resena")
    @SequenceGenerator(name = "seq_resena", sequenceName = "SEQ_RESENA", allocationSize = 1)
    @Column(name = "id_resena")
    private Long id;

    // De 1 a 5
    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    @Column(name = "comentario", length = 1000)
    private String comentario;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reserva", nullable = false, unique = true)
    private Reserva reserva;
}