package org.example.turismouq.Repository;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservaPagoRepository {

    private final JdbcTemplate jdbc;

    public ReservaPagoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** Ejecuta PKG_TURISMO_UQ.sp_reservar_y_pagar (reserva, servicio opcional y pago en una sola transacción). */
    public Map<String, Object> reservarYPagar(
            Long idCliente,
            Long idHabitacion,
            Date fechaIn,
            Date fechaOut,
            Integer numeroPersonas,
            BigDecimal valorPago,
            String metodoPago,
            Long idServicio,
            Integer cantidadServicio
    ) {
        return jdbc.execute((ConnectionCallback<Map<String, Object>>) con -> {
            try (CallableStatement cs = con.prepareCall(
                    "{call PKG_TURISMO_UQ.sp_reservar_y_pagar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

                cs.setLong(1, idCliente);
                cs.setLong(2, idHabitacion);
                cs.setDate(3, fechaIn);
                cs.setDate(4, fechaOut);
                cs.setInt(5, numeroPersonas);
                cs.setBigDecimal(6, valorPago);
                cs.setString(7, metodoPago);

                if (idServicio == null) {
                    cs.setNull(8, Types.NUMERIC);
                    cs.setNull(9, Types.NUMERIC);
                } else {
                    cs.setLong(8, idServicio);
                    if (cantidadServicio == null) {
                        cs.setNull(9, Types.NUMERIC);
                    } else {
                        cs.setInt(9, cantidadServicio);
                    }
                }

                cs.registerOutParameter(10, Types.NUMERIC);
                cs.registerOutParameter(11, Types.NUMERIC);
                cs.registerOutParameter(12, Types.VARCHAR);
                cs.registerOutParameter(13, Types.VARCHAR);
                cs.registerOutParameter(14, Types.NUMERIC);

                cs.execute();

                Map<String, Object> resultado = new LinkedHashMap<>();
                resultado.put("idReserva", cs.getLong(10));
                resultado.put("idPago", cs.getLong(11));
                resultado.put("resultadoServicio", cs.getString(12));
                resultado.put("detalleServicio", cs.getString(13));
                resultado.put("valorTotal", cs.getBigDecimal(14));
                return resultado;
            }
        });
    }

    /** Servicios del alojamiento al que pertenece la habitación. */
    public List<Map<String, Object>> serviciosDeHabitacion(Long idHabitacion) {
        return jdbc.queryForList("""
                SELECT s.id_servicio AS "idServicio",
                       s.nombre      AS "nombre",
                       s.precio      AS "precio"
                  FROM SERVICIO s
                  JOIN HABITACION h ON h.id_alojamiento = s.id_alojamiento
                 WHERE h.id_habitacion = ?
                 ORDER BY s.nombre
                """, idHabitacion);
    }
}