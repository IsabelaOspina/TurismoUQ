package org.example.turismouq.Model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ALOJAMIENTO",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_municipio", "nombre"}))
@Getter @Setter @NoArgsConstructor
public class Alojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alojamiento")
    @SequenceGenerator(name = "seq_alojamiento", sequenceName = "SEQ_ALOJAMIENTO", allocationSize = 1)
    @Column(name = "id_alojamiento")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "correo", length = 150)
    private String correo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_propietario", nullable = false)
    private Propietario propietario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_alojamiento", nullable = false)
    private TipoAlojamiento tipoAlojamiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;
}