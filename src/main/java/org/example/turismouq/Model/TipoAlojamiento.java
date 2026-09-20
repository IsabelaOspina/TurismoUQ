package org.example.turismouq.Model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIPO_ALOJAMIENTO")
@Getter @Setter @NoArgsConstructor
public class TipoAlojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_alojamiento")
    @SequenceGenerator(name = "seq_tipo_alojamiento", sequenceName = "SEQ_TIPO_ALOJAMIENTO", allocationSize = 1)
    @Column(name = "id_tipoAlojamiento")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = 250)
    private String descripcion;
}
