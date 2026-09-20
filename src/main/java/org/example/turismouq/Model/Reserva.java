package org.example.turismouq.Model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.example.turismouq.Model.Enums.*;

@Entity
@Table(name = "RESERVA")
@Getter @Setter @NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reserva")
    @SequenceGenerator(name = "seq_reserva", sequenceName = "SEQ_RESERVA", allocationSize = 1)
    @Column(name = "id_reserva")
    private Long id;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoReserva estado;

    @Column(name = "valor_total", nullable = false, precision = 14, scale = 2)
    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    // Esto reemplaza la tabla RESERVA_HABITACION
    @ManyToMany
    @JoinTable(name = "RESERVA_HABITACION",
            joinColumns = @JoinColumn(name = "id_reserva"),
            inverseJoinColumns = @JoinColumn(name = "id_habitacion"))
    private Set<Habitacion> habitaciones = new HashSet<>();
}
