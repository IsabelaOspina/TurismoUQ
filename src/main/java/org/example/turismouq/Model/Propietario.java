package org.example.turismouq.Model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PROPIETARIO")
@Getter @Setter @NoArgsConstructor
public class Propietario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_propietario")
    @SequenceGenerator(name = "seq_propietario", sequenceName = "SEQ_PROPIETARIO", allocationSize = 1)
    @Column(name = "id_propietario")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "cedula", nullable = false, length = 30, unique = true)
    private String cedula;

    @Column(name = "correo", nullable = false, length = 150, unique = true)
    private String correo;
}

