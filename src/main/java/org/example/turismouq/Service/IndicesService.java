package org.example.turismouq.Service;

import org.example.turismouq.Repository.IndicesRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndicesService {

    /** Ejecuciones por medición, para promediar el tiempo. */
    private static final int REPETICIONES = 50;

    /** Consulta del experimento y el índice que se prueba con ella. */
    public record Caso(
            int id,
            String titulo,
            String tipo,
            String consulta,
            String consultaForzada,
            String indice,
            String tabla,
            String crear,
            boolean basadoEnFuncion,
            boolean ayuda,
            String explicacion
    ) {
    }

    private static final List<Caso> CASOS = List.of(
            new Caso(1,
                    "Llegadas de un día por estado",
                    "Compuesto",
                    """
                    SELECT estado, COUNT(*) AS reservas
                      FROM RESERVA
                     WHERE fecha_entrada = DATE '2025-07-15'
                     GROUP BY estado""",
                    null,
                    "IDX_RESERVA_ENTRADA_ESTADO",
                    "RESERVA",
                    "CREATE INDEX idx_reserva_entrada_estado ON RESERVA (fecha_entrada, estado)",
                    false,
                    true,
                    "Sin índice se lee toda la partición del año. Con el índice se va directo al día y se cuenta sin leer la tabla."),
            new Caso(2,
                    "Cliente por correo sin importar mayúsculas",
                    "Basado en función",
                    """
                    SELECT id_cliente, nombre, cedula, correo
                      FROM CLIENTE
                     WHERE UPPER(correo) = UPPER('Cliente1500@TurismoUQ.com')""",
                    null,
                    "IDX_CLIENTE_CORREO_UPPER",
                    "CLIENTE",
                    "CREATE INDEX idx_cliente_correo_upper ON CLIENTE (UPPER(correo))",
                    true,
                    true,
                    "UPPER(correo) impide usar el índice normal de correo. El índice basado en función guarda el correo en mayúsculas y la búsqueda es directa."),
            new Caso(3,
                    "Pagos recibidos en un día",
                    "Compuesto",
                    """
                    SELECT COUNT(*) AS pagos, SUM(valor) AS recaudado
                      FROM PAGO
                     WHERE fecha = DATE '2025-07-01'""",
                    null,
                    "IDX_PAGO_FECHA_VALOR",
                    "PAGO",
                    "CREATE INDEX idx_pago_fecha_valor ON PAGO (fecha, valor)",
                    false,
                    true,
                    "Sin índice se leen todos los pagos. Con el índice se va directo a la fecha y se suma sin leer la tabla."),
            new Caso(4,
                    "Total de reservas finalizadas",
                    "No ayuda",
                    """
                    SELECT COUNT(*) AS reservas, SUM(valor_total) AS ingresos
                      FROM RESERVA
                     WHERE estado = 'FINALIZADA'""",
                    """
                    SELECT /*+ INDEX(r idx_reserva_estado) */ COUNT(*) AS reservas, SUM(valor_total) AS ingresos
                      FROM RESERVA r
                     WHERE estado = 'FINALIZADA'""",
                    "IDX_RESERVA_ESTADO",
                    "RESERVA",
                    "CREATE INDEX idx_reserva_estado ON RESERVA (estado)",
                    false,
                    false,
                    "FINALIZADA es cerca del 60 % de las reservas: leer la tabla completa es más barato que ir fila por fila "
                            + "desde el índice. Oracle lo ignora y, si se le obliga, el costo sube. Por eso se elimina.")
    );

    private final IndicesRepository indicesRepository;

    public IndicesService(IndicesRepository indicesRepository) {
        this.indicesRepository = indicesRepository;
    }

    public List<Map<String, Object>> casos() {
        return CASOS.stream().map(c -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", c.id());
            m.put("titulo", c.titulo());
            m.put("tipo", c.tipo());
            m.put("consulta", c.consulta());
            m.put("indice", c.indice());
            m.put("crear", c.crear());
            m.put("ayuda", c.ayuda());
            m.put("explicacion", c.explicacion());
            m.put("existe", indicesRepository.existeIndice(c.indice()));
            return m;
        }).toList();
    }

    /** Mide sin índice, crea el índice y mide de nuevo. Si no ayuda, mide forzándolo y lo elimina. */
    public Map<String, Object> comparar(int id) {
        Caso c = CASOS.stream().filter(x -> x.id() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No existe el caso " + id + "."));
        try {
            return indicesRepository.enUnaSesion(con -> {
                Map<String, Object> resultado = new LinkedHashMap<>();

                indicesRepository.eliminarIndice(con, c.indice());
                resultado.put("sinIndice", indicesRepository.medir(con, c.consulta(), REPETICIONES));

                indicesRepository.crearIndice(con, c.crear(), c.tabla(), c.basadoEnFuncion());
                resultado.put("conIndice", indicesRepository.medir(con, c.consulta(), REPETICIONES));

                if (c.consultaForzada() != null) {
                    resultado.put("forzado", indicesRepository.medir(con, c.consultaForzada(), REPETICIONES));
                }
                if (!c.ayuda()) {
                    indicesRepository.eliminarIndice(con, c.indice());
                }
                resultado.put("indiceQueda", c.ayuda());
                return resultado;
            });
        } catch (Exception e) {
            throw new RuntimeException(ErrorOracle.mensaje(e));
        }
    }
}