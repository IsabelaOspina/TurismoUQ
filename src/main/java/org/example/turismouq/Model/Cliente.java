package org.example.turismouq.Model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CLIENTE")
@Getter @Setter @NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente")
    @SequenceGenerator(name = "seq_cliente", sequenceName = "SEQ_CLIENTE", allocationSize = 1)
    @Column(name = "id_cliente")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "cedula", nullable = false, length = 30, unique = true)
    private String cedula;

    @Column(name = "correo", nullable = false, length = 150, unique = true)
    private String correo;
}

