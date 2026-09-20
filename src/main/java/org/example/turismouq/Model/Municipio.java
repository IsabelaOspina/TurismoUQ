package org.example.turismouq.Model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MUNICIPIO")
@Getter @Setter @NoArgsConstructor
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_municipio")
    @SequenceGenerator(name = "seq_municipio", sequenceName = "SEQ_MUNICIPIO", allocationSize = 1)
    @Column(name = "id_municipio")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = 250)
    private String descripcion;
}