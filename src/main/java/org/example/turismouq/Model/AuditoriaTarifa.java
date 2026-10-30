
package org.example.turismouq.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "AUDITORIA_TARIFA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaTarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AUDITORIA")
    private Long idAuditoria;

    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "FECHA_CAMBIO")
    private LocalDateTime fechaCambio;

    @Column(name = "TIPO_OPERACION")
    private String tipoOperacion;

    @Column(name = "ID_HABITACION")
    private Long idHabitacion;

    @Column(name = "ID_TEMPORADA")
    private Long idTemporada;

    @Column(name = "VALOR_ANTERIOR")
    private BigDecimal valorAnterior;

    @Column(name = "VALOR_NUEVO")
    private BigDecimal valorNuevo;
}
