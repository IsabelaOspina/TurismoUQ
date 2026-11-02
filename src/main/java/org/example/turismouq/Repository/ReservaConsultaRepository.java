package org.example.turismouq.Repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

/**
 * Consulta los datos de una reserva por su número, para precargar
 * el formulario de "Editar reserva" en el front.
 */
@Repository
public class ReservaConsultaRepository {

    private final JdbcTemplate jdbc;

    public ReservaConsultaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Map<String, Object>> buscarPorId(Long idReserva) {
        try {
            // Las fechas se devuelven como texto AAAA-MM-DD, el mismo formato que usa el formulario
            return Optional.of(jdbc.queryForMap("""
                    SELECT r.id_reserva                          AS "idReserva",
                           r.id_cliente                          AS "idCliente",
                           MIN(rh.id_habitacion)                 AS "idHabitacion",
                           TO_CHAR(r.fecha_entrada, 'YYYY-MM-DD') AS "fechaEntrada",
                           TO_CHAR(r.fecha_salida,  'YYYY-MM-DD') AS "fechaSalida",
                           r.numero_personas                     AS "numeroPersonas",
                           r.estado                              AS "estado",
                           r.valor_total                         AS "valorTotal"
                      FROM RESERVA r
                      LEFT JOIN RESERVA_HABITACION rh ON rh.id_reserva = r.id_reserva
                     WHERE r.id_reserva = ?
                     GROUP BY r.id_reserva, r.id_cliente, r.fecha_entrada, r.fecha_salida,
                              r.numero_personas, r.estado, r.valor_total
                    """, idReserva));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}