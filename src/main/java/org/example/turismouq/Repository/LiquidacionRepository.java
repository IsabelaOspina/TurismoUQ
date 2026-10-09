package org.example.turismouq.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class LiquidacionRepository {

    private final JdbcTemplate jdbc;

    public LiquidacionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** Ejecuta PKG_TURISMO_UQ.sp_liquidacion_mensual */
    public void generar(int anio, int mes) {
        jdbc.update("BEGIN PKG_TURISMO_UQ.sp_liquidacion_mensual(?, ?); END;", anio, mes);
    }

    /** Lee lo que el procedimiento guardó en LIQUIDACION_MENSUAL para ese mes. */
    public List<Map<String, Object>> consultar(int anio, int mes) {
        return jdbc.queryForList("""
                SELECT a.nombre          AS "alojamiento",
                       m.nombre          AS "municipio",
                       l.total_reservas  AS "totalReservas",
                       l.ingresos        AS "ingresos",
                       l.pagado          AS "pagado",
                       l.saldo_pendiente AS "saldoPendiente",
                       l.fecha_proceso   AS "fechaProceso"
                  FROM LIQUIDACION_MENSUAL l
                  JOIN ALOJAMIENTO a ON a.id_alojamiento = l.id_alojamiento
                  JOIN MUNICIPIO m   ON m.id_municipio   = a.id_municipio
                 WHERE l.anio = ? AND l.mes = ?
                 ORDER BY l.ingresos DESC
                """, anio, mes);
    }
}