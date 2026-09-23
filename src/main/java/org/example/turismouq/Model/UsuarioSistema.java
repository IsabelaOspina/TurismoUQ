package org.example.turismouq.Model;

import jakarta.persistence.*;
import lombok.*;
import org.example.turismouq.Model.Enums.*;


@Entity
@Table(name = "USUARIO_SISTEMA")
@Getter @Setter @NoArgsConstructor
public class UsuarioSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 150, unique = true)
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoUsuario estado;

    @Column(name = "cedula", nullable = false, length = 30, unique = true)
    private String cedula;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;
}

