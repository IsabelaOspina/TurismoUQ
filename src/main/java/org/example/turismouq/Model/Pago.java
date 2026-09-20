package org.example.turismouq.Model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.example.turismouq.Model.Enums.*;
import org.example.turismouq.Converter.*;


@Entity
@Table(name = "PAGO")
@Getter @Setter @NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pago")
    @SequenceGenerator(name = "seq_pago", sequenceName = "SEQ_PAGO", allocationSize = 1)
    @Column(name = "id_pago")
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "valor", nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    // En Java es true/false, en Oracle se guarda 'SI' o 'NO'
    @Convert(converter = SiNoConverter.class)
    @Column(name = "confirmado", nullable = false, length = 2)
    private Boolean confirmado;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false, length = 30)
    private MetodoPago metodoPago;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;
}
