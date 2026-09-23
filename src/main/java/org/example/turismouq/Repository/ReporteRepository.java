package org.example.turismouq.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.example.turismouq.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Consultas de reportes (1 a 8 del proyecto).
 */
@Repository
public interface ReporteRepository extends JpaRepository<Reserva, Long> {

    /** Una fila por reserva y alojamiento: evita contar dos veces las reservas con varias habitaciones. */
    String RESERVA_ALOJ = """
        WITH reserva_aloj AS (
            SELECT DISTINCT rh.id_reserva, h.id_alojamiento
            FROM RESERVA_HABITACION rh
            JOIN HABITACION h ON rh.id_habitacion = h.id_habitacion
        )
        """;

    // ------------------------------------------------------------
    // CONSULTA 1: Ocupación por municipio y mes (PIVOT)
    // ------------------------------------------------------------
    interface OcupacionPorMes {
        String getMunicipio();
        Long getEne(); Long getFeb(); Long getMar(); Long getAbr();
        Long getMay(); Long getJun(); Long getJul(); Long getAgo();
        Long getSep(); Long getOct(); Long getNov(); Long getDic();
    }

    @Query(value = RESERVA_ALOJ + """
        SELECT * FROM (
            SELECT m.nombre AS municipio,
                   TO_CHAR(r.fecha_entrada, 'MM') AS mes,
                   COUNT(*) AS totalReservas
            FROM RESERVA r
            JOIN reserva_aloj ra ON r.id_reserva = ra.id_reserva
            JOIN ALOJAMIENTO a ON ra.id_alojamiento = a.id_alojamiento
            JOIN MUNICIPIO m ON a.id_municipio = m.id_municipio
            WHERE r.estado IN ('CONFIRMADA', 'FINALIZADA')
            GROUP BY m.nombre, TO_CHAR(r.fecha_entrada, 'MM')
        )
        PIVOT (
            SUM(totalReservas)
            FOR mes IN ('01' AS Ene, '02' AS Feb, '03' AS Mar, '04' AS Abr,
                        '05' AS May, '06' AS Jun, '07' AS Jul, '08' AS Ago,
                        '09' AS Sep, '10' AS Oct, '11' AS Nov, '12' AS Dic)
        )
        ORDER BY municipio
        """, nativeQuery = true)
    List<OcupacionPorMes> ocupacionPorMunicipioYMes();

    // ------------------------------------------------------------
    // CONSULTA 2: Ingresos con ROLLUP + GROUPING
    // ------------------------------------------------------------
    interface IngresosRollup {
        String getMunicipio();
        String getTipoAlojamiento();
        String getTemporada();
        BigDecimal getIngresosTotales();
    }

    @Query(value = RESERVA_ALOJ + """
        SELECT
            CASE GROUPING(m.nombre) WHEN 1 THEN 'TOTAL' ELSE m.nombre END AS municipio,
            CASE GROUPING(t.nombre) WHEN 1 THEN 'TOTAL' ELSE t.nombre END AS tipoAlojamiento,
            CASE GROUPING(temp.tipo) WHEN 1 THEN 'TOTAL' ELSE NVL(temp.tipo, 'SIN TEMPORADA') END AS temporada,
            SUM(r.valor_total) AS ingresosTotales
        FROM RESERVA r
        JOIN reserva_aloj ra ON r.id_reserva = ra.id_reserva
        JOIN ALOJAMIENTO a ON ra.id_alojamiento = a.id_alojamiento
        JOIN MUNICIPIO m ON a.id_municipio = m.id_municipio
        JOIN TIPO_ALOJAMIENTO t ON a.id_tipo_alojamiento = t.id_tipo_alojamiento
        LEFT JOIN TEMPORADA temp ON r.fecha_entrada BETWEEN temp.fecha_inicio AND temp.fecha_fin
        WHERE r.estado IN ('CONFIRMADA', 'FINALIZADA')
        GROUP BY ROLLUP(m.nombre, t.nombre, temp.tipo)
        ORDER BY municipio, tipoAlojamiento, temporada
        """, nativeQuery = true)
    List<IngresosRollup> ingresosRollup();

    // ------------------------------------------------------------
    // CONSULTA 3: Top 3 alojamientos por municipio (RANK)
    // ------------------------------------------------------------
    interface TopAlojamiento {
        String getMunicipio();
        String getAlojamiento();
        BigDecimal getIngresos();
        Integer getRanking();
    }

    @Query(value = RESERVA_ALOJ + """
        SELECT municipio, alojamiento, ingresos, ranking
        FROM (
            SELECT m.nombre AS municipio,
                   a.nombre AS alojamiento,
                   SUM(r.valor_total) AS ingresos,
                   RANK() OVER (PARTITION BY m.nombre ORDER BY SUM(r.valor_total) DESC) AS ranking
            FROM RESERVA r
            JOIN reserva_aloj ra ON r.id_reserva = ra.id_reserva
            JOIN ALOJAMIENTO a ON ra.id_alojamiento = a.id_alojamiento
            JOIN MUNICIPIO m ON a.id_municipio = m.id_municipio
            WHERE r.estado IN ('CONFIRMADA', 'FINALIZADA')
            GROUP BY m.nombre, a.nombre
        )
        WHERE ranking <= 3
        ORDER BY municipio, ranking
        """, nativeQuery = true)
    List<TopAlojamiento> top3AlojamientosPorMunicipio();

    // ------------------------------------------------------------
    // CONSULTA 4: Variación de ingresos mes contra mes (LAG)
    // ------------------------------------------------------------
    interface VariacionMensual {
        String getMes();
        BigDecimal getIngresos();
        BigDecimal getIngresosMesAnterior();      // null en el primer mes
        BigDecimal getVariacion();                // null en el primer mes
        BigDecimal getVariacionPorcentaje();      // null en el primer mes
    }

    @Query(value = """
        SELECT
            mes,
            ingresos,
            LAG(ingresos) OVER (ORDER BY mes) AS ingresosMesAnterior,
            ingresos - LAG(ingresos) OVER (ORDER BY mes) AS variacion,
            ROUND(
                (ingresos - LAG(ingresos) OVER (ORDER BY mes))
                / NULLIF(LAG(ingresos) OVER (ORDER BY mes), 0) * 100, 2
            ) AS variacionPorcentaje
        FROM (
            SELECT TO_CHAR(fecha_entrada, 'YYYY-MM') AS mes,
                   SUM(valor_total) AS ingresos
            FROM RESERVA
            WHERE estado IN ('CONFIRMADA', 'FINALIZADA')
            GROUP BY TO_CHAR(fecha_entrada, 'YYYY-MM')
        )
        ORDER BY mes
        """, nativeQuery = true)
    List<VariacionMensual> variacionMensual();

    // ------------------------------------------------------------
    // CONSULTA 5: Reservas en un rango de fechas (variables de enlace)
    // ------------------------------------------------------------
    interface ReservaDetalle {
        Long getIdReserva();
        Date getFechaEntrada();
        Date getFechaSalida();
        BigDecimal getValorTotal();
        String getEstado();
        String getCliente();
        String getMunicipio();
    }

    @Query(value = """
        SELECT DISTINCT
            r.id_reserva AS idReserva,
            r.fecha_entrada AS fechaEntrada,
            r.fecha_salida AS fechaSalida,
            r.valor_total AS valorTotal,
            r.estado AS estado,
            c.nombre AS cliente,
            m.nombre AS municipio
        FROM RESERVA r
        JOIN CLIENTE c ON r.id_cliente = c.id_cliente
        JOIN RESERVA_HABITACION rh ON r.id_reserva = rh.id_reserva
        JOIN HABITACION h ON rh.id_habitacion = h.id_habitacion
        JOIN ALOJAMIENTO a ON h.id_alojamiento = a.id_alojamiento
        JOIN MUNICIPIO m ON a.id_municipio = m.id_municipio
        WHERE r.fecha_entrada BETWEEN :fechaInicio AND :fechaFin
        ORDER BY r.fecha_entrada
        """, nativeQuery = true)
    List<ReservaDetalle> reservasPorFechas(@Param("fechaInicio") LocalDate fechaInicio,
                                           @Param("fechaFin") LocalDate fechaFin);

    // ------------------------------------------------------------
    // CONSULTA 6: Vista materializada de ocupación mensual
    // La vista se crea UNA vez con el script SQL (CREATE MATERIALIZED VIEW).
    // Aquí solo se lee y se refresca.
    // ------------------------------------------------------------
    interface OcupacionMensualMv {
        String getMes();
        String getMunicipio();
        Long getTotalReservas();
        BigDecimal getIngresosTotales();
    }

    @Query(value = """
        SELECT mes,
               municipio,
               total_reservas AS totalReservas,
               ingresos_totales AS ingresosTotales
        FROM mv_ocupacion_mensual
        ORDER BY mes, municipio
        """, nativeQuery = true)
    List<OcupacionMensualMv> ocupacionMensual();

    @Modifying
    @Transactional
    @Query(value = "BEGIN DBMS_MVIEW.REFRESH('MV_OCUPACION_MENSUAL', 'C'); END;", nativeQuery = true)

    void refrescarOcupacionMensual();

    // ------------------------------------------------------------
    // CONSULTA 7: PIVOT + UNPIVOT por trimestre
    // ------------------------------------------------------------
    interface ReservasPorTrimestre {
        String getMunicipio();
        String getTrimestre();
        Long getTotalReservas();
    }

    @Query(value = RESERVA_ALOJ + """
        SELECT municipio, trimestre, total_reservas AS totalReservas
        FROM (
            SELECT m.nombre AS municipio,
                   CASE
                       WHEN TO_CHAR(r.fecha_entrada, 'MM') IN ('01','02','03') THEN 'Q1'
                       WHEN TO_CHAR(r.fecha_entrada, 'MM') IN ('04','05','06') THEN 'Q2'
                       WHEN TO_CHAR(r.fecha_entrada, 'MM') IN ('07','08','09') THEN 'Q3'
                       ELSE 'Q4'
                   END AS trimestre,
                   COUNT(*) AS total
            FROM RESERVA r
            JOIN reserva_aloj ra ON r.id_reserva = ra.id_reserva
            JOIN ALOJAMIENTO a ON ra.id_alojamiento = a.id_alojamiento
            JOIN MUNICIPIO m ON a.id_municipio = m.id_municipio
            WHERE r.estado IN ('CONFIRMADA', 'FINALIZADA')
            GROUP BY m.nombre,
                   CASE
                       WHEN TO_CHAR(r.fecha_entrada, 'MM') IN ('01','02','03') THEN 'Q1'
                       WHEN TO_CHAR(r.fecha_entrada, 'MM') IN ('04','05','06') THEN 'Q2'
                       WHEN TO_CHAR(r.fecha_entrada, 'MM') IN ('07','08','09') THEN 'Q3'
                       ELSE 'Q4'
                   END
        )
        PIVOT (
            SUM(total)
            FOR trimestre IN ('Q1' AS Q1, 'Q2' AS Q2, 'Q3' AS Q3, 'Q4' AS Q4)
        )
        UNPIVOT (
            total_reservas FOR trimestre IN (Q1, Q2, Q3, Q4)
        )
        ORDER BY municipio, trimestre
        """, nativeQuery = true)
    List<ReservasPorTrimestre> reservasPorTrimestre();

    // ------------------------------------------------------------
    // CONSULTA 8: Mejores alojamientos por calificación
    // ------------------------------------------------------------
    interface AlojamientoCalificado {
        String getAlojamiento();
        String getMunicipio();
        BigDecimal getCalificacionPromedio();
        Long getTotalResenas();
    }

    @Query(value = RESERVA_ALOJ + """
        SELECT a.nombre AS alojamiento,
               m.nombre AS municipio,
               ROUND(AVG(re.calificacion), 2) AS calificacionPromedio,
               COUNT(re.id_resena) AS totalResenas
        FROM RESENA re
        JOIN reserva_aloj ra ON re.id_reserva = ra.id_reserva
        JOIN ALOJAMIENTO a ON ra.id_alojamiento = a.id_alojamiento
        JOIN MUNICIPIO m ON a.id_municipio = m.id_municipio
        GROUP BY a.nombre, m.nombre
        HAVING COUNT(re.id_resena) >= 3
        ORDER BY calificacionPromedio DESC, totalResenas DESC
        """, nativeQuery = true)
    List<AlojamientoCalificado> mejoresAlojamientos();
}