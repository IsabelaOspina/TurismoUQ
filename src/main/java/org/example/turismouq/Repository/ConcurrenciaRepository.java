package org.example.turismouq.Repository;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ConcurrenciaRepository {

    private final JdbcTemplate jdbc;

    public ConcurrenciaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Ejecuta sp_reservar_con_bloqueo o sp_reservar_sin_bloqueo.
     * Cada llamada usa su propia conexión del pool, es decir, su propia sesión de Oracle.
     */
    public Map<String, Object> reservar(
            boolean conBloqueo,
            Long idCliente,
            Long idHabitacion,
            Date fechaIn,
            Date fechaOut,
            Integer numeroPersonas,
            Integer segundosEspera
    ) {
        String procedimiento = conBloqueo ? "sp_reservar_con_bloqueo" : "sp_reservar_sin_bloqueo";

        return jdbc.execute((ConnectionCallback<Map<String, Object>>) con -> {
            try (CallableStatement cs = con.prepareCall(
                    "{call " + procedimiento + "(?, ?, ?, ?, ?, ?, ?, ?)}")) {

                cs.setLong(1, idCliente);
                cs.setLong(2, idHabitacion);
                cs.setDate(3, fechaIn);
                cs.setDate(4, fechaOut);
                cs.setInt(5, numeroPersonas);
                cs.setInt(6, segundosEspera);
                cs.registerOutParameter(7, Types.NUMERIC);
                cs.registerOutParameter(8, Types.NUMERIC);

                cs.execute();

                Map<String, Object> resultado = new LinkedHashMap<>();
                resultado.put("idReserva", cs.getLong(7));
                resultado.put("msBloqueo", cs.getLong(8));
                return resultado;
            }
        });
    }

    /** Ejecuta sp_limpiar_prueba_concurrencia y devuelve cuántas reservas borró. */
    public int limpiar(Long idHabitacion, Date fechaIn, Date fechaOut) {
        return jdbc.execute((ConnectionCallback<Integer>) con -> {
            try (CallableStatement cs = con.prepareCall("{call sp_limpiar_prueba_concurrencia(?, ?, ?, ?)}")) {
                cs.setLong(1, idHabitacion);
                cs.setDate(2, fechaIn);
                cs.setDate(3, fechaOut);
                cs.registerOutParameter(4, Types.NUMERIC);
                cs.execute();
                return cs.getInt(4);
            }
        });
    }

    /** Reservas activas de la habitación que se cruzan con esas fechas. */
    public List<Map<String, Object>> reservasQueSeCruzan(Long idHabitacion, Date fechaIn, Date fechaOut) {
        return jdbc.queryForList("""
                SELECT r.id_reserva                        AS "idReserva",
                       r.id_cliente                        AS "idCliente",
                       TO_CHAR(r.fecha_entrada, 'YYYY-MM-DD') AS "fechaEntrada",
                       TO_CHAR(r.fecha_salida, 'YYYY-MM-DD')  AS "fechaSalida",
                       r.estado                            AS "estado"
                  FROM RESERVA r
                  JOIN RESERVA_HABITACION rh ON rh.id_reserva = r.id_reserva
                 WHERE rh.id_habitacion = ?
                   AND r.estado IN ('CONFIRMADA', 'PENDIENTE')
                   AND r.fecha_entrada < ?
                   AND r.fecha_salida  > ?
                 ORDER BY r.id_reserva
                """, idHabitacion, fechaOut, fechaIn);
    }
}